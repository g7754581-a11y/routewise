package com.routewise.service;

import com.routewise.dsa.sorting.QuickSort;
import com.routewise.dto.ScheduleResponseDTO;
import com.routewise.entity.Bus;
import com.routewise.entity.Route;
import com.routewise.entity.Schedule;
import com.routewise.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final SeatService seatService;

    public ScheduleService(ScheduleRepository scheduleRepository, SeatService seatService) {
        this.scheduleRepository = scheduleRepository;
        this.seatService = seatService;
    }

    public List<ScheduleResponseDTO> searchSchedules(String source, String destination, LocalDate journeyDate,
                                                     String sortBy, String busTypeFilter) {
        List<Schedule> schedules;
        if (journeyDate != null) {
            LocalDateTime start = journeyDate.atStartOfDay();
            LocalDateTime end = journeyDate.plusDays(1).atStartOfDay();
            schedules = scheduleRepository.searchSchedules(source, destination, start, end);
        } else {
            schedules = scheduleRepository.findBySourceAndDestination(source, destination);
        }

        List<ScheduleResponseDTO> dtos = new ArrayList<>();
        for (Schedule s : schedules) {
            // Apply bus type filter if present
            if (busTypeFilter != null && !busTypeFilter.isEmpty() && !"ALL".equalsIgnoreCase(busTypeFilter)) {
                if (!s.getBus().getBusType().toLowerCase().contains(busTypeFilter.toLowerCase())) {
                    continue;
                }
            }
            dtos.add(convertToDTO(s));
        }

        // Custom manual sorting using DSA QuickSort implementation
        if (sortBy != null && !dtos.isEmpty()) {
            Comparator<ScheduleResponseDTO> comparator;
            switch (sortBy.toLowerCase()) {
                case "price":
                case "price_asc":
                case "lowest_price":
                    comparator = Comparator.comparingDouble(ScheduleResponseDTO::getBaseFare);
                    break;
                case "duration":
                case "shortest_duration":
                    comparator = Comparator.comparingInt(ScheduleResponseDTO::getDurationMinutes);
                    break;
                case "departure":
                case "earliest_departure":
                    comparator = Comparator.comparing(ScheduleResponseDTO::getDepartureTime);
                    break;
                case "seats":
                case "available_seats":
                    comparator = (a, b) -> Long.compare(b.getAvailableSeats(), a.getAvailableSeats());
                    break;
                default:
                    comparator = Comparator.comparing(ScheduleResponseDTO::getDepartureTime);
            }
            // Use manual QuickSort generic implementation
            QuickSort.sort(dtos, comparator);
        }

        return dtos;
    }

    public List<ScheduleResponseDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleRepository.findAll();
        List<ScheduleResponseDTO> dtos = new ArrayList<>();
        for (Schedule s : schedules) {
            dtos.add(convertToDTO(s));
        }
        return dtos;
    }

    public Optional<Schedule> getScheduleById(Long id) {
        return scheduleRepository.findById(id);
    }

    public ScheduleResponseDTO getScheduleDTOById(Long id) {
        Schedule s = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found with id: " + id));
        return convertToDTO(s);
    }

    @Transactional
    public Schedule createSchedule(Schedule schedule) {
        Schedule saved = scheduleRepository.save(schedule);
        // Automatically generate all seats for this schedule
        seatService.generateSeatsForSchedule(saved);
        return saved;
    }

    @Transactional
    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }

    public ScheduleResponseDTO convertToDTO(Schedule s) {
        ScheduleResponseDTO dto = new ScheduleResponseDTO();
        dto.setId(s.getId());
        dto.setBusId(s.getBus().getId());
        dto.setBusName(s.getBus().getBusName());
        dto.setBusNumber(s.getBus().getBusNumber());
        dto.setBusType(s.getBus().getBusType());
        dto.setTotalSeats(s.getBus().getTotalSeats());
        dto.setSeatLayoutType(s.getBus().getSeatLayoutType());
        dto.setAmenities(s.getBus().getAmenities());

        dto.setRouteId(s.getRoute().getId());
        dto.setSourceCity(s.getRoute().getSourceCity().getName());
        dto.setDestinationCity(s.getRoute().getDestinationCity().getName());
        dto.setDistanceKm(s.getRoute().getDistanceKm());
        dto.setRouteDurationMins(s.getRoute().getEstimatedDurationMins());

        dto.setDepartureTime(s.getDepartureTime());
        dto.setArrivalTime(s.getArrivalTime());

        int durationMins = (int) ChronoUnit.MINUTES.between(s.getDepartureTime(), s.getArrivalTime());
        if (durationMins <= 0) durationMins = s.getRoute().getEstimatedDurationMins();
        dto.setDurationMinutes(durationMins);

        dto.setBaseFare(s.getBaseFare());
        dto.setStatus(s.getStatus());

        long avail = seatService.getAvailableSeatsCount(s.getId());
        long booked = seatService.getBookedSeatsCount(s.getId());
        dto.setAvailableSeats(avail);
        dto.setBookedSeats(booked);

        return dto;
    }

    public long countActiveSchedules() {
        return scheduleRepository.countByStatusNot("CANCELLED");
    }
}
