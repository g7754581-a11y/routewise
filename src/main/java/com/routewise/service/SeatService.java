package com.routewise.service;

import com.routewise.entity.Schedule;
import com.routewise.entity.Seat;
import com.routewise.repository.SeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeatService {

    private final SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public List<Seat> getSeatsForSchedule(Long scheduleId) {
        return seatRepository.findByScheduleIdOrderBySeatNumberAsc(scheduleId);
    }

    public long getAvailableSeatsCount(Long scheduleId) {
        return seatRepository.countByScheduleIdAndIsBookedFalseAndIsBlockedFalse(scheduleId);
    }

    public long getBookedSeatsCount(Long scheduleId) {
        return seatRepository.countByScheduleIdAndIsBookedTrue(scheduleId);
    }

    @Transactional
    public List<Seat> generateSeatsForSchedule(Schedule schedule) {
        int totalSeats = schedule.getBus().getTotalSeats() != null ? schedule.getBus().getTotalSeats() : 40;
        double baseFare = schedule.getBaseFare() != null ? schedule.getBaseFare() : 500.0;
        List<Seat> seats = new ArrayList<>();

        char[] seatCols = new char[]{'A', 'B', 'C', 'D'};
        int rows = (int) Math.ceil((double) totalSeats / 4.0);

        int count = 0;
        for (int r = 1; r <= rows && count < totalSeats; r++) {
            for (char c : seatCols) {
                if (count >= totalSeats) break;
                String seatNumber = r + String.valueOf(c);
                // Last row or premium front row slight adjustment or standard
                double price = baseFare;
                String seatType = schedule.getBus().getBusType().toLowerCase().contains("sleeper") ? "SLEEPER" : "SEATER";

                Seat seat = new Seat(schedule, seatNumber, "LOWER", seatType, false, false, price);
                seats.add(seat);
                count++;
            }
        }
        return seatRepository.saveAll(seats);
    }

    @Transactional
    public boolean lockSeat(Long seatId) {
        return seatRepository.bookSeatAtomically(seatId) > 0;
    }

    @Transactional
    public void releaseSeat(Long seatId) {
        seatRepository.releaseSeatAtomically(seatId);
    }
}
