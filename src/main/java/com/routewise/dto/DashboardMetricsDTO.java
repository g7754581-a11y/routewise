package com.routewise.dto;

public class DashboardMetricsDTO {
    private long totalPassengers;
    private long totalBuses;
    private long activeSchedules;
    private long totalBookings;
    private long confirmedBookings;
    private long cancelledBookings;
    private double totalRevenue;
    private double occupancyRate;
    private long waitlistCount;

    public DashboardMetricsDTO() {}

    public long getTotalPassengers() { return totalPassengers; }
    public void setTotalPassengers(long totalPassengers) { this.totalPassengers = totalPassengers; }

    public long getTotalBuses() { return totalBuses; }
    public void setTotalBuses(long totalBuses) { this.totalBuses = totalBuses; }

    public long getActiveSchedules() { return activeSchedules; }
    public void setActiveSchedules(long activeSchedules) { this.activeSchedules = activeSchedules; }

    public long getTotalBookings() { return totalBookings; }
    public void setTotalBookings(long totalBookings) { this.totalBookings = totalBookings; }

    public long getConfirmedBookings() { return confirmedBookings; }
    public void setConfirmedBookings(long confirmedBookings) { this.confirmedBookings = confirmedBookings; }

    public long getCancelledBookings() { return cancelledBookings; }
    public void setCancelledBookings(long cancelledBookings) { this.cancelledBookings = cancelledBookings; }

    public double getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(double totalRevenue) { this.totalRevenue = totalRevenue; }

    public double getOccupancyRate() { return occupancyRate; }
    public void setOccupancyRate(double occupancyRate) { this.occupancyRate = occupancyRate; }

    public long getWaitlistCount() { return waitlistCount; }
    public void setWaitlistCount(long waitlistCount) { this.waitlistCount = waitlistCount; }
}
