package com.routewise.dto;

public class SeatDTO {
    private Long id;
    private Long scheduleId;
    private String seatNumber;
    private String deck;
    private String seatType;
    private Boolean isBooked;
    private Boolean isBlocked;
    private Double price;

    public SeatDTO() {}

    public SeatDTO(Long id, Long scheduleId, String seatNumber, String deck, String seatType, Boolean isBooked, Boolean isBlocked, Double price) {
        this.id = id;
        this.scheduleId = scheduleId;
        this.seatNumber = seatNumber;
        this.deck = deck;
        this.seatType = seatType;
        this.isBooked = isBooked;
        this.isBlocked = isBlocked;
        this.price = price;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getScheduleId() { return scheduleId; }
    public void setScheduleId(Long scheduleId) { this.scheduleId = scheduleId; }

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

