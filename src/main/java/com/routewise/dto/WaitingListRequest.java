package com.routewise.dto;

public class WaitingListRequest {
    private Long scheduleId;
    private String passengerName;
    private String passengerEmail;
    private String contact;
    private Integer seatsRequested;
    private Integer priorityLevel; // 1 = Normal, 2 = Senior/Medical, 3 = VIP

    public WaitingListRequest() {}

    public Long getScheduleId() { return scheduleId; }
    public void setScheduleId(Long scheduleId) { this.scheduleId = scheduleId; }

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
}
