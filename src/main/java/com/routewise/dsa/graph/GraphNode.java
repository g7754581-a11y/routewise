package com.routewise.dsa.graph;

public class GraphNode {
    private Long id;
    private String name;
    private String code;
    private Double latitude;
    private Double longitude;

    public GraphNode(Long id, String name, String code, Double latitude, Double longitude) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getCode() { return code; }
    public Double getLatitude() { return latitude; }
    public Double getLongitude() { return longitude; }
}
