package com.routewise.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "route_stops")
public class RouteStop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", nullable = false)
    @JsonIgnore
    private Route route;

    @Column(nullable = false, length = 100)
    private String stopName;

    @Column(nullable = false)
    private Integer stopOrder;

    private Double distanceFromSourceKm;

    private Integer haltDurationMins = 10;

    public RouteStop() {}

    public RouteStop(Route route, String stopName, Integer stopOrder, Double distanceFromSourceKm, Integer haltDurationMins) {
        this.route = route;
        this.stopName = stopName;
        this.stopOrder = stopOrder;
        this.distanceFromSourceKm = distanceFromSourceKm;
        this.haltDurationMins = haltDurationMins != null ? haltDurationMins : 10;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    @JsonIgnore
    public Route getRoute() { return route; }
    public void setRoute(Route route) { this.route = route; }

    public String getStopName() { return stopName; }
    public void setStopName(String stopName) { this.stopName = stopName; }

    public Integer getStopOrder() { return stopOrder; }
    public void setStopOrder(Integer stopOrder) { this.stopOrder = stopOrder; }

    public Double getDistanceFromSourceKm() { return distanceFromSourceKm; }
    public void setDistanceFromSourceKm(Double distanceFromSourceKm) { this.distanceFromSourceKm = distanceFromSourceKm; }

    public Integer getHaltDurationMins() { return haltDurationMins; }
    public void setHaltDurationMins(Integer haltDurationMins) { this.haltDurationMins = haltDurationMins; }
}
