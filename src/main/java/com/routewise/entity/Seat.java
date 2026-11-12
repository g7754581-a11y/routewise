package com.routewise.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(
    name = "seats",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_schedule_seat", columnNames = {"schedule_id", "seat_number"})
    }
)
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    @JsonIgnore
    private Schedule schedule;

    @Column(name = "seat_number", nullable = false, length = 10)
    private String seatNumber;

    @Column(length = 20)
    private String deck = "LOWER"; // LOWER, UPPER

    @Column(length = 30)
    private String seatType = "SEATER"; // SEATER, SLEEPER_SINGLE, SLEEPER_DOUBLE

    @Column(nullable = false)
    private Boolean isBooked = false;

    @Column(nullable = false)
    private Boolean isBlocked = false;

    @Column(nullable = false)
    private Double price;

    public Seat() {}

    public Seat(Schedule schedule, String seatNumber, String deck, String seatType, Boolean isBooked, Boolean isBlocked, Double price) {
        this.schedule = schedule;
        this.seatNumber = seatNumber;
        this.deck = deck != null ? deck : "LOWER";
        this.seatType = seatType != null ? seatType : "SEATER";
        this.isBooked = isBooked != null ? isBooked : false;
        this.isBlocked = isBlocked != null ? isBlocked : false;
        this.price = price;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    @JsonIgnore
    public Schedule getSchedule() { return schedule; }
    public void setSchedule(Schedule schedule) { this.schedule = schedule; }

    public com.routewise.dto.SeatDTO toDTO() {
        Long schedId = schedule != null ? schedule.getId() : null;
        return new com.routewise.dto.SeatDTO(id, schedId, seatNumber, deck, seatType, isBooked, isBlocked, price);
    }

    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }

    public String getDeck() { return deck; }
    public void setDeck(String deck) { this.deck = deck; }

    public String getSeatType() { return seatType; }
    public void setSeatType(String seatType) { this.seatType = seatType; }

    public Boolean getIsBooked() { return isBooked; }
    public void setIsBooked(Boolean isBooked) { this.isBooked = isBooked; }

    public Boolean getIsBlocked() { return isBlocked; }
    public void setIsBlocked(Boolean isBlocked) { this.isBlocked = isBlocked; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}
