package com.routewise.controller;

import com.routewise.dto.UserDTO;
import com.routewise.entity.Bus;
import com.routewise.entity.Role;
import com.routewise.service.BusService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/buses")
public class BusController {

    private final BusService busService;

    public BusController(BusService busService) {
        this.busService = busService;
    }

    @GetMapping
    public List<Bus> getAllBuses() {
        return busService.getAllBuses();
    }

    @GetMapping("/active")
    public List<Bus> getActiveBuses() {
        return busService.getActiveBuses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBusById(@PathVariable Long id) {
        return busService.getBusById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createBus(@RequestBody Bus bus, HttpSession session) {
        if (!isAdmin(session)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "Admin access required"));
        }
        Bus created = busService.saveBus(bus);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBus(@PathVariable Long id, @RequestBody Bus bus, HttpSession session) {
        if (!isAdmin(session)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "Admin access required"));
        }
        try {
            Bus updated = busService.updateBus(id, bus);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBus(@PathVariable Long id, HttpSession session) {
        if (!isAdmin(session)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "Admin access required"));
        }
        busService.deleteBus(id);
        return ResponseEntity.ok(Map.of("message", "Bus deleted successfully"));
    }

    @PostMapping("/{id}/toggle")
    public ResponseEntity<?> toggleBusStatus(@PathVariable Long id, HttpSession session) {
        if (!isAdmin(session)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "Admin access required"));
        }
        Bus updated = busService.toggleBusStatus(id);
        return ResponseEntity.ok(updated);
    }

    private boolean isAdmin(HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute(AuthController.SESSION_USER_KEY);
        return user != null && user.getRole() == Role.ROLE_ADMIN;
    }
}
