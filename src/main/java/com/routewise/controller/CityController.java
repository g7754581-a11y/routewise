package com.routewise.controller;

import com.routewise.entity.City;
import com.routewise.service.RouteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CityController {

    private final RouteService routeService;

    public CityController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping
    public List<City> getAllCities() {
        return routeService.getAllCities();
    }
}
