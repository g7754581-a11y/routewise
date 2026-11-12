package com.routewise.dto;

import java.util.List;

public class BookingRequest {
    private Long scheduleId;
    private List<String> seatNumbers;
    private String passengerName;
    private String passengerEmail;
    private String passengerPhone;
    private Integer passengerAge;
    private String passengerGender;

    public BookingRequest() {}

    public Long getScheduleId() { return scheduleId; }
    public void setScheduleId(Long scheduleId) { this.scheduleId = scheduleId; }

    public List<String> getSeatNumbers() { return seatNumbers; }
    public void setSeatNumbers(List<String> seatNumbers) { this.seatNumbers = seatNumbers; }

    public String getPassengerName() { return passengerName; }
    public void setPassengerName(String passengerName) { this.passengerName = passengerName; }

    public String getPassengerEmail() { return passengerEmail; }
    public void setPassengerEmail(String passengerEmail) { this.passengerEmail = passengerEmail; }

    public String getPassengerPhone() { return passengerPhone; }
    public void setPassengerPhone(String passengerPhone) { this.passengerPhone = passengerPhone; }

    public Integer getPassengerAge() { return passengerAge; }
    public void setPassengerAge(Integer passengerAge) { this.passengerAge = passengerAge; }

    public String getPassengerGender() { return passengerGender; }
    public void setPassengerGender(String passengerGender) { this.passengerGender = passengerGender; }
}
