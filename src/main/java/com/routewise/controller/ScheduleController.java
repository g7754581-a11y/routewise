package com.routewise.controller;

import com.routewise.dto.ScheduleResponseDTO;
import com.routewise.dto.SeatDTO;
import com.routewise.dto.UserDTO;
import com.routewise.entity.Bus;
import com.routewise.entity.Role;
import com.routewise.entity.Route;
import com.routewise.entity.Schedule;
import com.routewise.entity.Seat;
import com.routewise.service.BusService;
import com.routewise.service.RouteService;
import com.routewise.service.ScheduleService;
import com.routewise.service.SeatService;
import jakarta.servlet.http.HttpSession;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final SeatService seatService;
    private final BusService busService;
    private final RouteService routeService;

    public ScheduleController(ScheduleService scheduleService,
                              SeatService seatService,
                              BusService busService,
                              RouteService routeService) {
        this.scheduleService = scheduleService;
        this.seatService = seatService;
        this.busService = busService;
        this.routeService = routeService;
    }

    @GetMapping("/search")
    public List<ScheduleResponseDTO> searchSchedules(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false, defaultValue = "earliest_departure") String sortBy,
            @RequestParam(required = false) String busType) {

        return scheduleService.searchSchedules(from, to, date, sortBy, busType);
    }

    @GetMapping
    public List<ScheduleResponseDTO> getAllSchedules() {
        return scheduleService.getAllSchedules();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getScheduleById(@PathVariable Long id) {
        try {
            ScheduleResponseDTO dto = scheduleService.getScheduleDTOById(id);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/seats")
    public List<SeatDTO> getSeatsForSchedule(@PathVariable Long id) {
        return seatService.getSeatsForSchedule(id).stream().map(Seat::toDTO).toList();
    }

    @PostMapping
    public ResponseEntity<?> createSchedule(@RequestBody Map<String, Object> payload, HttpSession session) {
        if (!isAdmin(session)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "Admin access required"));
        }
        try {
            Long busId = ((Number) payload.get("busId")).longValue();
            Long routeId = ((Number) payload.get("routeId")).longValue();
            LocalDateTime departureTime = LocalDateTime.parse((String) payload.get("departureTime"));
            LocalDateTime arrivalTime = LocalDateTime.parse((String) payload.get("arrivalTime"));
            Double baseFare = ((Number) payload.get("baseFare")).doubleValue();
            String status = payload.get("status") != null ? (String) payload.get("status") : "SCHEDULED";

            Bus bus = busService.getBusById(busId)
                    .orElseThrow(() -> new IllegalArgumentException("Bus not found with id: " + busId));
            Route route = routeService.getRouteById(routeId)
                    .orElseThrow(() -> new IllegalArgumentException("Route not found with id: " + routeId));

            Schedule schedule = new Schedule(bus, route, departureTime, arrivalTime, baseFare, status);
            Schedule created = scheduleService.createSchedule(schedule);

            return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.convertToDTO(created));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSchedule(@PathVariable Long id, HttpSession session) {
        if (!isAdmin(session)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "Admin access required"));
        }
        scheduleService.deleteSchedule(id);
        return ResponseEntity.ok(Map.of("message", "Schedule deleted successfully"));
    }

    private boolean isAdmin(HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute(AuthController.SESSION_USER_KEY);
        return user != null && user.getRole() == Role.ROLE_ADMIN;
    }
}
