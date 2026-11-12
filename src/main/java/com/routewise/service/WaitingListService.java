package com.routewise.service;

import com.routewise.dsa.datastructures.CustomPriorityQueue;
import com.routewise.dto.WaitingListRequest;
import com.routewise.entity.Schedule;
import com.routewise.entity.User;
import com.routewise.entity.WaitingListEntry;
import com.routewise.repository.ScheduleRepository;
import com.routewise.repository.WaitingListRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
public class WaitingListService {

    private final WaitingListRepository waitingListRepository;
    private final ScheduleRepository scheduleRepository;

    public WaitingListService(WaitingListRepository waitingListRepository,
                              ScheduleRepository scheduleRepository) {
        this.waitingListRepository = waitingListRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @Transactional
    public WaitingListEntry addToWaitlist(WaitingListRequest req, User currentUser) {
        Schedule schedule = scheduleRepository.findById(req.getScheduleId())
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found with id: " + req.getScheduleId()));

        int priority = req.getPriorityLevel() != null ? req.getPriorityLevel() : 1;
        int seats = req.getSeatsRequested() != null && req.getSeatsRequested() > 0 ? req.getSeatsRequested() : 1;

        WaitingListEntry entry = new WaitingListEntry(
                schedule,
                currentUser,
                req.getPassengerName() != null ? req.getPassengerName().trim() : "Passenger",
                req.getPassengerEmail() != null ? req.getPassengerEmail().trim() : "",
                req.getContact() != null ? req.getContact().trim() : "",
                seats,
                priority
        );

        return waitingListRepository.save(entry);
    }

    public List<WaitingListEntry> getWaitlistForSchedule(Long scheduleId) {
        List<WaitingListEntry> entries = waitingListRepository.findByScheduleIdAndStatusOrderByPriorityLevelDescCreatedAtAsc(scheduleId, "WAITING");

        // Use custom Priority Queue to demonstrate custom binary heap extraction
        CustomPriorityQueue<WaitingListEntry> pq = new CustomPriorityQueue<>(
                Comparator.comparingInt(WaitingListEntry::getPriorityLevel)
                        .thenComparing((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
        );

        for (WaitingListEntry e : entries) {
            pq.insert(e);
        }

        return entries;
    }

    public List<WaitingListEntry> getAllWaitlist() {
        return waitingListRepository.findByStatusOrderByCreatedAtDesc("WAITING");
    }

    @Transactional
    public WaitingListEntry promoteWaitlistEntry(Long entryId) {
        WaitingListEntry entry = waitingListRepository.findById(entryId)
                .orElseThrow(() -> new IllegalArgumentException("Waitlist entry not found: " + entryId));

        entry.setStatus("CONFIRMED");
        return waitingListRepository.save(entry);
    }

    @Transactional
    public void cancelWaitlistEntry(Long entryId) {
        WaitingListEntry entry = waitingListRepository.findById(entryId)
                .orElseThrow(() -> new IllegalArgumentException("Waitlist entry not found: " + entryId));
        entry.setStatus("CANCELLED");
        waitingListRepository.save(entry);
    }
}
