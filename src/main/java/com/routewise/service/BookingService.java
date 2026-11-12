package com.routewise.service;

import com.routewise.dsa.datastructures.CustomHashTable;
import com.routewise.dto.BookingRequest;
import com.routewise.dto.BookingResponseDTO;
import com.routewise.dto.DashboardMetricsDTO;
import com.routewise.entity.*;
import com.routewise.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingSeatRepository bookingSeatRepository;
    private final ScheduleRepository scheduleRepository;
    private final SeatRepository seatRepository;
    private final UserRepository userRepository;
    private final BusRepository busRepository;
    private final WaitingListRepository waitingListRepository;

    // Custom Hash Table for O(1) in-memory PNR indexing and lookup
    private final CustomHashTable<String, Booking> pnrCache = new CustomHashTable<>(64, 0.75);

    public BookingService(BookingRepository bookingRepository,
                          BookingSeatRepository bookingSeatRepository,
                          ScheduleRepository scheduleRepository,
                          SeatRepository seatRepository,
                          UserRepository userRepository,
                          BusRepository busRepository,
                          WaitingListRepository waitingListRepository) {
        this.bookingRepository = bookingRepository;
        this.bookingSeatRepository = bookingSeatRepository;
        this.scheduleRepository = scheduleRepository;
        this.seatRepository = seatRepository;
        this.userRepository = userRepository;
        this.busRepository = busRepository;
        this.waitingListRepository = waitingListRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public BookingResponseDTO createBooking(BookingRequest req, User currentUser) {
        if (req.getScheduleId() == null) {
            throw new IllegalArgumentException("Schedule ID is required");
        }
        if (req.getSeatNumbers() == null || req.getSeatNumbers().isEmpty()) {
            throw new IllegalArgumentException("At least one seat must be selected");
        }
        if (req.getPassengerName() == null || req.getPassengerName().trim().isEmpty()) {
            throw new IllegalArgumentException("Passenger name is required");
        }

        Schedule schedule = scheduleRepository.findById(req.getScheduleId())
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found: " + req.getScheduleId()));

        if ("CANCELLED".equalsIgnoreCase(schedule.getStatus())) {
            throw new IllegalStateException("This schedule has been cancelled");
        }

        // Validate and atomically reserve all requested seats
        List<Seat> reservedSeats = new ArrayList<>();
        double totalFare = 0.0;

        for (String seatNum : req.getSeatNumbers()) {
            Seat seat = seatRepository.findByScheduleIdAndSeatNumber(schedule.getId(), seatNum.trim().toUpperCase())
                    .orElseThrow(() -> new IllegalArgumentException("Seat " + seatNum + " does not exist on this bus"));

            if (Boolean.TRUE.equals(seat.getIsBooked()) || Boolean.TRUE.equals(seat.getIsBlocked())) {
                throw new IllegalStateException("Seat " + seatNum + " has already been booked by another passenger. Please choose an available seat.");
            }

            int rowsAffected = seatRepository.bookSeatAtomically(seat.getId());
            if (rowsAffected == 0) {
                throw new IllegalStateException("Seat " + seatNum + " was just claimed by another passenger. Transaction cancelled.");
            }

            seat.setIsBooked(true);
            reservedSeats.add(seat);
            totalFare += (seat.getPrice() != null ? seat.getPrice() : schedule.getBaseFare());
        }

        // Add 5% GST & service charge
        double serviceFee = Math.round(totalFare * 0.05 * 100.0) / 100.0;
        double finalAmount = Math.round((totalFare + serviceFee) * 100.0) / 100.0;

        // Generate unique PNR
        String pnr = generateUniquePnr();

        Booking booking = new Booking(
                pnr,
                currentUser,
                schedule,
                finalAmount,
                req.getPassengerName().trim(),
                req.getPassengerEmail() != null ? req.getPassengerEmail().trim() : "",
                req.getPassengerPhone() != null ? req.getPassengerPhone().trim() : "",
                reservedSeats.size()
        );

        Booking savedBooking = bookingRepository.save(booking);

        List<BookingSeat> bookingSeats = new ArrayList<>();
        for (Seat seat : reservedSeats) {
            BookingSeat bs = new BookingSeat(savedBooking, seat, seat.getSeatNumber(), seat.getPrice());
            bookingSeats.add(bookingSeatRepository.save(bs));
        }
        savedBooking.setBookingSeats(bookingSeats);

        // Store into Custom Hash Table
        pnrCache.put(savedBooking.getPnr(), savedBooking);

        return convertToDTO(savedBooking);
    }

    public BookingResponseDTO getBookingByPnr(String pnr) {
        if (pnr == null || pnr.trim().isEmpty()) {
            throw new IllegalArgumentException("PNR cannot be empty");
        }
        String cleanPnr = pnr.trim().toUpperCase();

        // 1. Try Custom Hash Table
        Booking cached = pnrCache.get(cleanPnr);
        if (cached != null) {
            return convertToDTO(cached);
        }

        // 2. Fallback to Database
        Booking booking = bookingRepository.findByPnr(cleanPnr)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found with PNR: " + cleanPnr));

        // Index in hash table for subsequent queries
        pnrCache.put(booking.getPnr(), booking);
        return convertToDTO(booking);
    }

    @Transactional
    public BookingResponseDTO cancelBooking(String pnr, User user) {
        String cleanPnr = pnr.trim().toUpperCase();
        Booking booking = bookingRepository.findByPnr(cleanPnr)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found with PNR: " + cleanPnr));

        // If regular passenger, ensure they own the booking
        if (user != null && user.getRole() == Role.ROLE_PASSENGER) {
            if (booking.getUser() != null && !booking.getUser().getId().equals(user.getId())) {
                throw new IllegalStateException("Unauthorized: You can only cancel your own bookings");
            }
        }

        if ("CANCELLED".equalsIgnoreCase(booking.getBookingStatus())) {
            throw new IllegalStateException("This booking has already been cancelled");
        }

        // Release all booked seats
        List<BookingSeat> seats = bookingSeatRepository.findByBookingId(booking.getId());
        for (BookingSeat bs : seats) {
            seatRepository.releaseSeatAtomically(bs.getSeat().getId());
        }

        booking.setBookingStatus("CANCELLED");
        booking.setPaymentStatus("REFUNDED");
        Booking saved = bookingRepository.save(booking);

        // Update in Custom Hash Table
        pnrCache.put(saved.getPnr(), saved);

        // Auto-check waitlist for this schedule
        checkAndPromoteWaitlist(booking.getSchedule().getId());

        return convertToDTO(saved);
    }

    public List<BookingResponseDTO> getUserBookings(Long userId) {
        List<Booking> list = bookingRepository.findByUserIdOrderByCreatedAtDesc(userId);
        List<BookingResponseDTO> dtos = new ArrayList<>();
        for (Booking b : list) {
            dtos.add(convertToDTO(b));
        }
        return dtos;
    }

    public List<BookingResponseDTO> getAllBookings() {
        List<Booking> list = bookingRepository.findAllByOrderByCreatedAtDesc();
        List<BookingResponseDTO> dtos = new ArrayList<>();
        for (Booking b : list) {
            dtos.add(convertToDTO(b));
        }
        return dtos;
    }

    public List<BookingResponseDTO> searchBookings(String keyword) {
        List<Booking> list;
        if (keyword == null || keyword.trim().isEmpty()) {
            list = bookingRepository.findAllByOrderByCreatedAtDesc();
        } else {
            list = bookingRepository.searchBookings(keyword.trim());
        }
        List<BookingResponseDTO> dtos = new ArrayList<>();
        for (Booking b : list) {
            dtos.add(convertToDTO(b));
        }
        return dtos;
    }

    public DashboardMetricsDTO getDashboardMetrics() {
        DashboardMetricsDTO dto = new DashboardMetricsDTO();
        dto.setTotalPassengers(userRepository.countByRole(Role.ROLE_PASSENGER));
        dto.setTotalBuses(busRepository.countByIsActiveTrue());
        dto.setActiveSchedules(scheduleRepository.countByStatusNot("CANCELLED"));
        dto.setTotalBookings(bookingRepository.count());
        dto.setConfirmedBookings(bookingRepository.countByBookingStatus("CONFIRMED"));
        dto.setCancelledBookings(bookingRepository.countByBookingStatus("CANCELLED"));

        Double revenue = bookingRepository.calculateTotalRevenue();
        dto.setTotalRevenue(revenue != null ? Math.round(revenue * 100.0) / 100.0 : 0.0);

        long waitlistCount = waitingListRepository.count();
        dto.setWaitlistCount(waitlistCount);

        // Calculate occupancy rate
        long totalCapacity = 0;
        long totalBooked = 0;
        List<Schedule> activeSchedules = scheduleRepository.findAll();
        for (Schedule s : activeSchedules) {
            if (!"CANCELLED".equalsIgnoreCase(s.getStatus())) {
                totalCapacity += (s.getBus().getTotalSeats() != null ? s.getBus().getTotalSeats() : 40);
                totalBooked += seatRepository.countByScheduleIdAndIsBookedTrue(s.getId());
            }
        }
        double occupancy = totalCapacity > 0 ? ((double) totalBooked / totalCapacity) * 100.0 : 0.0;
        dto.setOccupancyRate(Math.round(occupancy * 10.0) / 10.0);

        return dto;
    }

    private void checkAndPromoteWaitlist(Long scheduleId) {
        List<WaitingListEntry> waitingList = waitingListRepository.findByScheduleIdAndStatusOrderByPriorityLevelDescCreatedAtAsc(scheduleId, "WAITING");
        if (waitingList.isEmpty()) return;

        long availableSeats = seatRepository.countByScheduleIdAndIsBookedFalseAndIsBlockedFalse(scheduleId);
        for (WaitingListEntry entry : waitingList) {
            if (entry.getSeatsRequested() <= availableSeats) {
                entry.setStatus("CONFIRMED");
                waitingListRepository.save(entry);
                break;
            }
        }
    }

    private String generateUniquePnr() {
        String chars = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
        Random random = new Random();
        String pnr;
        do {
            StringBuilder sb = new StringBuilder("RW-");
            for (int i = 0; i < 6; i++) {
                sb.append(chars.charAt(random.nextInt(chars.length())));
            }
            pnr = sb.toString();
        } while (bookingRepository.findByPnr(pnr).isPresent());
        return pnr;
    }

    public BookingResponseDTO convertToDTO(Booking b) {
        BookingResponseDTO dto = new BookingResponseDTO();
        dto.setId(b.getId());
        dto.setPnr(b.getPnr());
        dto.setScheduleId(b.getSchedule().getId());
        dto.setBusName(b.getSchedule().getBus().getBusName());
        dto.setBusNumber(b.getSchedule().getBus().getBusNumber());
        dto.setBusType(b.getSchedule().getBus().getBusType());
        dto.setSourceCity(b.getSchedule().getRoute().getSourceCity().getName());
        dto.setDestinationCity(b.getSchedule().getRoute().getDestinationCity().getName());
        dto.setDepartureTime(b.getSchedule().getDepartureTime());
        dto.setArrivalTime(b.getSchedule().getArrivalTime());
        dto.setSeatsCount(b.getSeatsCount());
        dto.setTotalAmount(b.getTotalAmount());
        dto.setPaymentStatus(b.getPaymentStatus());
        dto.setBookingStatus(b.getBookingStatus());
        dto.setPassengerName(b.getPassengerName());
        dto.setPassengerEmail(b.getPassengerEmail());
        dto.setPassengerPhone(b.getPassengerPhone());
        dto.setBookingDate(b.getBookingDate());

        List<String> seatNums = new ArrayList<>();
        if (b.getBookingSeats() != null) {
            for (BookingSeat bs : b.getBookingSeats()) {
                seatNums.add(bs.getSeatNumber());
            }
        }
        dto.setSeatNumbers(seatNums);
        return dto;
    }
}
