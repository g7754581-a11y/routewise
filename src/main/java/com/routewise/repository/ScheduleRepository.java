package com.routewise.repository;

import com.routewise.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("SELECT s FROM Schedule s WHERE LOWER(s.route.sourceCity.name) = LOWER(:source) " +
           "AND LOWER(s.route.destinationCity.name) = LOWER(:destination) " +
           "AND s.departureTime >= :startTime AND s.departureTime < :endTime " +
           "AND s.status NOT IN ('CANCELLED') " +
           "ORDER BY s.departureTime ASC")
    List<Schedule> searchSchedules(
            @Param("source") String source,
            @Param("destination") String destination,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

    @Query("SELECT s FROM Schedule s WHERE LOWER(s.route.sourceCity.name) = LOWER(:source) " +
           "AND LOWER(s.route.destinationCity.name) = LOWER(:destination) " +
           "AND s.status NOT IN ('CANCELLED') " +
           "ORDER BY s.departureTime ASC")
    List<Schedule> findBySourceAndDestination(
            @Param("source") String source,
            @Param("destination") String destination
    );

    List<Schedule> findByDepartureTimeAfterAndStatusNot(LocalDateTime time, String status);

    long countByStatusNot(String status);
}
