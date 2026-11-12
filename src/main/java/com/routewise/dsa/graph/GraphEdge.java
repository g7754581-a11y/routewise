package com.routewise.dsa.graph;

public class GraphEdge implements Comparable<GraphEdge> {
    private String source;
    private String destination;
    private double distanceKm;
    private int durationMins;
    private double baseFare;

    public GraphEdge(String source, String destination, double distanceKm, int durationMins, double baseFare) {
        this.source = source;
        this.destination = destination;
        this.distanceKm = distanceKm;
        this.durationMins = durationMins;
        this.baseFare = baseFare;
    }

    public String getSource() { return source; }
    public String getDestination() { return destination; }
    public double getDistanceKm() { return distanceKm; }
    public int getDurationMins() { return durationMins; }
    public double getBaseFare() { return baseFare; }

    @Override
    public int compareTo(GraphEdge other) {
        return Double.compare(this.distanceKm, other.distanceKm);
    }
}
