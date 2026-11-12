package com.routewise.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "routes")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "source_city_id", nullable = false)
    private City sourceCity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "destination_city_id", nullable = false)
    private City destinationCity;

    @Column(nullable = false)
    private Double distanceKm;

    @Column(nullable = false)
    private Integer estimatedDurationMins;

    @Column(nullable = false)
    private Boolean isActive = true;

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("stopOrder ASC")
    @JsonIgnoreProperties("route")
    private List<RouteStop> stops = new ArrayList<>();

    public Route() {}

    public Route(City sourceCity, City destinationCity, Double distanceKm, Integer estimatedDurationMins, Boolean isActive) {
        this.sourceCity = sourceCity;
        this.destinationCity = destinationCity;
        this.distanceKm = distanceKm;
        this.estimatedDurationMins = estimatedDurationMins;
        this.isActive = isActive != null ? isActive : true;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public City getSourceCity() { return sourceCity; }
    public void setSourceCity(City sourceCity) { this.sourceCity = sourceCity; }

    public City getDestinationCity() { return destinationCity; }
    public void setDestinationCity(City destinationCity) { this.destinationCity = destinationCity; }

    public Double getDistanceKm() { return distanceKm; }
    public void setDistanceKm(Double distanceKm) { this.distanceKm = distanceKm; }

    public Integer getEstimatedDurationMins() { return estimatedDurationMins; }
    public void setEstimatedDurationMins(Integer estimatedDurationMins) { this.estimatedDurationMins = estimatedDurationMins; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public List<RouteStop> getStops() { return stops; }
    public void setStops(List<RouteStop> stops) { this.stops = stops; }
}
