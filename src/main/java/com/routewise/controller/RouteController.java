package com.routewise.controller;

import com.routewise.dsa.graph.GraphEdge;
import com.routewise.dsa.graph.RoutePathResult;
import com.routewise.dto.UserDTO;
import com.routewise.entity.City;
import com.routewise.entity.Role;
import com.routewise.entity.Route;
import com.routewise.entity.RouteStop;
import com.routewise.service.RouteOptimizationService;
import com.routewise.service.RouteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/routes")
public class RouteController {

    private final RouteService routeService;
    private final RouteOptimizationService routeOptimizationService;

    public RouteController(RouteService routeService, RouteOptimizationService routeOptimizationService) {
        this.routeService = routeService;
        this.routeOptimizationService = routeOptimizationService;
    }

    @GetMapping
    public List<Route> getAllRoutes() {
        return routeService.getAllRoutes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRouteById(@PathVariable Long id) {
        return routeService.getRouteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/stops")
    public List<RouteStop> getRouteStops(@PathVariable Long id) {
        return routeService.getRouteStops(id);
    }

    @PostMapping
    public ResponseEntity<?> createRoute(@RequestBody Route route, HttpSession session) {
        if (!isAdmin(session)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "Admin access required"));
        }
        Route created = routeService.saveRoute(route);
        routeOptimizationService.refreshGraph();
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/{id}/stops")
    public ResponseEntity<?> addStop(@PathVariable Long id, @RequestBody Map<String, Object> payload, HttpSession session) {
        if (!isAdmin(session)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "Admin access required"));
        }
        String stopName = (String) payload.get("stopName");
        int stopOrder = payload.get("stopOrder") != null ? ((Number) payload.get("stopOrder")).intValue() : 1;
        double distanceKm = payload.get("distanceKm") != null ? ((Number) payload.get("distanceKm")).doubleValue() : 0.0;
        int haltDuration = payload.get("haltDurationMins") != null ? ((Number) payload.get("haltDurationMins")).intValue() : 10;

        RouteStop stop = routeService.addRouteStop(id, stopName, stopOrder, distanceKm, haltDuration);
        return ResponseEntity.status(HttpStatus.CREATED).body(stop);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoute(@PathVariable Long id, HttpSession session) {
        if (!isAdmin(session)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "Admin access required"));
        }
        routeService.deleteRoute(id);
        routeOptimizationService.refreshGraph();
        return ResponseEntity.ok(Map.of("message", "Route deleted successfully"));
    }

    @PostMapping("/{id}/toggle")
    public ResponseEntity<?> toggleRoute(@PathVariable Long id, HttpSession session) {
        if (!isAdmin(session)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "Admin access required"));
        }
        Route updated = routeService.toggleRouteStatus(id);
        routeOptimizationService.refreshGraph();
        return ResponseEntity.ok(updated);
    }

    // =========================================================================
    // ROUTE OPTIMIZATION & GRAPH ENDPOINTS
    // =========================================================================

    @GetMapping("/explore")
    public ResponseEntity<?> exploreRoute(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam(defaultValue = "DISTANCE") String metric) {

        RoutePathResult result;
        if ("DURATION".equalsIgnoreCase(metric)) {
            result = routeOptimizationService.findFastestDurationRoute(from, to);
        } else if ("STOPS".equalsIgnoreCase(metric) || "FEWEST_TRANSFERS".equalsIgnoreCase(metric)) {
            result = routeOptimizationService.findFewestTransfersRoute(from, to);
        } else {
            result = routeOptimizationService.findShortestDistanceRoute(from, to);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/alternative-paths")
    public ResponseEntity<?> getAlternativePaths(@RequestParam String from, @RequestParam String to) {
        List<RoutePathResult> paths = routeOptimizationService.findAllAlternativeRoutes(from, to);
        return ResponseEntity.ok(paths);
    }

    @GetMapping("/network-mst")
    public ResponseEntity<?> getMinimumSpanningTree() {
        List<GraphEdge> mst = routeOptimizationService.getMinimumSpanningNetwork();
        return ResponseEntity.ok(mst);
    }

    @GetMapping("/negative-cycle-check")
    public ResponseEntity<?> checkNegativeCycles(@RequestParam String from, @RequestParam String to) {
        RoutePathResult result = routeOptimizationService.checkNegativeCycles(from, to);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/topology")
    public ResponseEntity<?> getTopology() {
        return ResponseEntity.ok(routeOptimizationService.getNetworkTopology());
    }

    private boolean isAdmin(HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute(AuthController.SESSION_USER_KEY);
        return user != null && user.getRole() == Role.ROLE_ADMIN;
    }
}
