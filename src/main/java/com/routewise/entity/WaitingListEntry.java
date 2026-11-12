package com.routewise.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "waiting_list")
public class WaitingListEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 100)
    private String passengerName;

    @Column(length = 120)
    private String passengerEmail;

    @Column(nullable = false, length = 20)
    private String contact;

    @Column(nullable = false)
    private Integer seatsRequested = 1;

    @Column(nullable = false)
    private Integer priorityLevel = 1; // 1 = Normal, 2 = Senior/Medical, 3 = VIP/Emergency

    @Column(nullable = false, length = 30)
    private String status = "WAITING"; // WAITING, CONFIRMED, CANCELLED

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public WaitingListEntry() {}

    public WaitingListEntry(Schedule schedule, User user, String passengerName, String passengerEmail,
                            String contact, Integer seatsRequested, Integer priorityLevel) {
        this.schedule = schedule;
        this.user = user;
        this.passengerName = passengerName;
        this.passengerEmail = passengerEmail;
        this.contact = contact;
        this.seatsRequested = seatsRequested != null ? seatsRequested : 1;
        this.priorityLevel = priorityLevel != null ? priorityLevel : 1;
        this.status = "WAITING";
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Schedule getSchedule() { return schedule; }
    public void setSchedule(Schedule schedule) { this.schedule = schedule; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getPassengerName() { return passengerName; }
    public void setPassengerName(String passengerName) { this.passengerName = passengerName; }

    public String getPassengerEmail() { return passengerEmail; }
    public void setPassengerEmail(String passengerEmail) { this.passengerEmail = passengerEmail; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public Integer getSeatsRequested() { return seatsRequested; }
    public void setSeatsRequested(Integer seatsRequested) { this.seatsRequested = seatsRequested; }

    public Integer getPriorityLevel() { return priorityLevel; }
    public void setPriorityLevel(Integer priorityLevel) { this.priorityLevel = priorityLevel; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
