package com.routewise.service;

import com.routewise.entity.City;
import com.routewise.entity.Route;
import com.routewise.entity.RouteStop;
import com.routewise.repository.CityRepository;
import com.routewise.repository.RouteRepository;
import com.routewise.repository.RouteStopRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RouteService {

    private final RouteRepository routeRepository;
    private final RouteStopRepository routeStopRepository;
    private final CityRepository cityRepository;

    public RouteService(RouteRepository routeRepository,
                        RouteStopRepository routeStopRepository,
                        CityRepository cityRepository) {
        this.routeRepository = routeRepository;
        this.routeStopRepository = routeStopRepository;
        this.cityRepository = cityRepository;
    }

    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }

    public List<Route> getActiveRoutes() {
        return routeRepository.findByIsActiveTrue();
    }

    public Optional<Route> getRouteById(Long id) {
        return routeRepository.findById(id);
    }

    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    public Optional<City> getCityByName(String name) {
        return cityRepository.findByNameIgnoreCase(name);
    }

    public List<RouteStop> getRouteStops(Long routeId) {
        return routeStopRepository.findByRouteIdOrderByStopOrderAsc(routeId);
    }

    @Transactional
    public Route saveRoute(Route route) {
        return routeRepository.save(route);
    }

    @Transactional
    public RouteStop addRouteStop(Long routeId, String stopName, int stopOrder, double distanceKm, int haltDuration) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new IllegalArgumentException("Route not found with id: " + routeId));

        RouteStop stop = new RouteStop(route, stopName, stopOrder, distanceKm, haltDuration);
        return routeStopRepository.save(stop);
    }

    @Transactional
    public void deleteRoute(Long id) {
        routeRepository.deleteById(id);
    }

    @Transactional
    public Route toggleRouteStatus(Long id) {
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Route not found with id: " + id));
        route.setIsActive(!Boolean.TRUE.equals(route.getIsActive()));
        return routeRepository.save(route);
    }
}
