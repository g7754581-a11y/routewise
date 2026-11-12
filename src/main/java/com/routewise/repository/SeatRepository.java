package com.routewise.repository;

import com.routewise.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findByScheduleIdOrderBySeatNumberAsc(Long scheduleId);

    Optional<Seat> findByScheduleIdAndSeatNumber(Long scheduleId, String seatNumber);

    long countByScheduleIdAndIsBookedFalseAndIsBlockedFalse(Long scheduleId);

    long countByScheduleIdAndIsBookedTrue(Long scheduleId);

    @Modifying
    @Query("UPDATE Seat s SET s.isBooked = true WHERE s.id = :seatId AND s.isBooked = false AND s.isBlocked = false")
    int bookSeatAtomically(@Param("seatId") Long seatId);

    @Modifying
    @Query("UPDATE Seat s SET s.isBooked = false WHERE s.id = :seatId")
    int releaseSeatAtomically(@Param("seatId") Long seatId);
}
