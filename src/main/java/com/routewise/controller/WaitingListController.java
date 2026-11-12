package com.routewise.controller;

import com.routewise.dto.UserDTO;
import com.routewise.dto.WaitingListRequest;
import com.routewise.entity.Role;
import com.routewise.entity.User;
import com.routewise.entity.WaitingListEntry;
import com.routewise.service.AuthService;
import com.routewise.service.WaitingListService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/waiting-list")
public class WaitingListController {

    private final WaitingListService waitingListService;
    private final AuthService authService;

    public WaitingListController(WaitingListService waitingListService, AuthService authService) {
        this.waitingListService = waitingListService;
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<?> joinWaitingList(@RequestBody WaitingListRequest request, HttpSession session) {
        try {
            UserDTO sessionUser = (UserDTO) session.getAttribute(AuthController.SESSION_USER_KEY);
            User currentUser = null;
            if (sessionUser != null) {
                currentUser = authService.findById(sessionUser.getId()).orElse(null);
            }
            WaitingListEntry entry = waitingListService.addToWaitlist(request, currentUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(entry);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllWaitlist(HttpSession session) {
        if (!isAdmin(session)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "Admin access required"));
        }
        return ResponseEntity.ok(waitingListService.getAllWaitlist());
    }

    @GetMapping("/schedule/{scheduleId}")
    public List<WaitingListEntry> getWaitlistForSchedule(@PathVariable Long scheduleId) {
        return waitingListService.getWaitlistForSchedule(scheduleId);
    }

    @PostMapping("/{id}/promote")
    public ResponseEntity<?> promoteEntry(@PathVariable Long id, HttpSession session) {
        if (!isAdmin(session)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "Admin access required"));
        }
        try {
            WaitingListEntry promoted = waitingListService.promoteWaitlistEntry(id);
            return ResponseEntity.ok(promoted);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<?> cancelEntry(@PathVariable Long id, HttpSession session) {
        if (!isAdmin(session)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "Admin access required"));
        }
        waitingListService.cancelWaitlistEntry(id);
        return ResponseEntity.ok(Map.of("message", "Waitlist entry cancelled"));
    }

    private boolean isAdmin(HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute(AuthController.SESSION_USER_KEY);
        return user != null && user.getRole() == Role.ROLE_ADMIN;
    }
}
