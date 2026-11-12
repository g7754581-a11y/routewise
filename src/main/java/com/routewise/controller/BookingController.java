package com.routewise.controller;

import com.routewise.dto.BookingRequest;
import com.routewise.dto.BookingResponseDTO;
import com.routewise.dto.UserDTO;
import com.routewise.entity.Role;
import com.routewise.entity.User;
import com.routewise.service.AuthService;
import com.routewise.service.BookingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final AuthService authService;

    public BookingController(BookingService bookingService, AuthService authService) {
        this.bookingService = bookingService;
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody BookingRequest request, HttpSession session) {
        try {
            UserDTO sessionUser = (UserDTO) session.getAttribute(AuthController.SESSION_USER_KEY);
            User currentUser = null;
            if (sessionUser != null) {
                currentUser = authService.findById(sessionUser.getId()).orElse(null);
            }
            BookingResponseDTO response = bookingService.createBooking(request, currentUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to complete booking: " + e.getMessage()));
        }
    }

    @GetMapping("/{pnr}")
    public ResponseEntity<?> getBookingByPnr(@PathVariable String pnr) {
        try {
            BookingResponseDTO dto = bookingService.getBookingByPnr(pnr);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/{pnr}/cancel")
    public ResponseEntity<?> cancelBooking(@PathVariable String pnr, HttpSession session) {
        try {
            UserDTO sessionUser = (UserDTO) session.getAttribute(AuthController.SESSION_USER_KEY);
            User currentUser = null;
            if (sessionUser != null) {
                currentUser = authService.findById(sessionUser.getId()).orElse(null);
            }
            BookingResponseDTO dto = bookingService.cancelBooking(pnr, currentUser);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/my-bookings")
    public ResponseEntity<?> getMyBookings(HttpSession session) {
        UserDTO sessionUser = (UserDTO) session.getAttribute(AuthController.SESSION_USER_KEY);
        if (sessionUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Please log in to view your bookings"));
        }
        List<BookingResponseDTO> bookings = bookingService.getUserBookings(sessionUser.getId());
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllBookings(HttpSession session) {
        if (!isAdmin(session)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "Admin access required"));
        }
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchBookings(@RequestParam(required = false) String keyword, HttpSession session) {
        if (!isAdmin(session)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "Admin access required"));
        }
        return ResponseEntity.ok(bookingService.searchBookings(keyword));
    }

    private boolean isAdmin(HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute(AuthController.SESSION_USER_KEY);
        return user != null && user.getRole() == Role.ROLE_ADMIN;
    }
}
