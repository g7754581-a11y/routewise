package com.routewise.dsa.graph;

import java.util.List;

public class RoutePathResult {
    private List<String> path;
    private double totalDistanceKm;
    private int totalDurationMins;
    private double estimatedFare;
    private int intermediateStopsCount;
    private String algorithmUsed;
    private boolean reachable;
    private String note;

    public RoutePathResult() {}

    public RoutePathResult(List<String> path, double totalDistanceKm, int totalDurationMins,
                           double estimatedFare, int intermediateStopsCount, String algorithmUsed,
                           boolean reachable, String note) {
        this.path = path;
        this.totalDistanceKm = totalDistanceKm;
        this.totalDurationMins = totalDurationMins;
        this.estimatedFare = estimatedFare;
        this.intermediateStopsCount = intermediateStopsCount;
        this.algorithmUsed = algorithmUsed;
        this.reachable = reachable;
        this.note = note;
    }

    public List<String> getPath() { return path; }
    public void setPath(List<String> path) { this.path = path; }

    public double getTotalDistanceKm() { return totalDistanceKm; }
    public void setTotalDistanceKm(double totalDistanceKm) { this.totalDistanceKm = totalDistanceKm; }

    public int getTotalDurationMins() { return totalDurationMins; }
    public void setTotalDurationMins(int totalDurationMins) { this.totalDurationMins = totalDurationMins; }

    public double getEstimatedFare() { return estimatedFare; }
    public void setEstimatedFare(double estimatedFare) { this.estimatedFare = estimatedFare; }

    public int getIntermediateStopsCount() { return intermediateStopsCount; }
    public void setIntermediateStopsCount(int intermediateStopsCount) { this.intermediateStopsCount = intermediateStopsCount; }

    public String getAlgorithmUsed() { return algorithmUsed; }
    public void setAlgorithmUsed(String algorithmUsed) { this.algorithmUsed = algorithmUsed; }

    public boolean isReachable() { return reachable; }
    public void setReachable(boolean reachable) { this.reachable = reachable; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}
