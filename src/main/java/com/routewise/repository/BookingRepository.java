package com.routewise.repository;

import com.routewise.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findByPnr(String pnr);

    List<Booking> findByUserIdOrderByCreatedAtDesc(Long userId);

    List<Booking> findAllByOrderByCreatedAtDesc();

    @Query("SELECT COALESCE(SUM(b.totalAmount), 0.0) FROM Booking b WHERE b.bookingStatus = 'CONFIRMED'")
    Double calculateTotalRevenue();

    long countByBookingStatus(String bookingStatus);

    @Query("SELECT b FROM Booking b WHERE LOWER(b.pnr) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(b.passengerName) LIKE LOWER(CONCAT('%', :keyword, '%')) ORDER BY b.createdAt DESC")
    List<Booking> searchBookings(@Param("keyword") String keyword);
}
