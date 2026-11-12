package com.routewise.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "buses")
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String busNumber;

    @Column(nullable = false, length = 100)
    private String busName;

    @Column(nullable = false, length = 50)
    private String busType; // e.g., "Volvo AC Multi-Axle", "BharatBenz Luxury", "AC Sleeper", "Non-AC Seater"

    @Column(nullable = false)
    private Integer totalSeats = 40;

    @Column(length = 20)
    private String seatLayoutType = "2x2";

    @Column(length = 255)
    private String amenities; // e.g., "WiFi, Charging Point, Blankets, Water Bottle"

    @Column(nullable = false)
    private Boolean isActive = true;

    public Bus() {}

    public Bus(String busNumber, String busName, String busType, Integer totalSeats, String seatLayoutType, String amenities, Boolean isActive) {
        this.busNumber = busNumber;
        this.busName = busName;
        this.busType = busType;
        this.totalSeats = totalSeats;
        this.seatLayoutType = seatLayoutType != null ? seatLayoutType : "2x2";
        this.amenities = amenities;
        this.isActive = isActive != null ? isActive : true;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBusNumber() { return busNumber; }
    public void setBusNumber(String busNumber) { this.busNumber = busNumber; }

    public String getBusName() { return busName; }
    public void setBusName(String busName) { this.busName = busName; }

    public String getBusType() { return busType; }
    public void setBusType(String busType) { this.busType = busType; }

    public Integer getTotalSeats() { return totalSeats; }
    public void setTotalSeats(Integer totalSeats) { this.totalSeats = totalSeats; }

    public String getSeatLayoutType() { return seatLayoutType; }
    public void setSeatLayoutType(String seatLayoutType) { this.seatLayoutType = seatLayoutType; }

    public String getAmenities() { return amenities; }
    public void setAmenities(String amenities) { this.amenities = amenities; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}
