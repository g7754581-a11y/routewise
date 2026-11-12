package com.routewise.service;

import com.routewise.dsa.graph.GraphEdge;
import com.routewise.dsa.graph.GraphNode;
import com.routewise.dsa.graph.RoutePathResult;
import com.routewise.dsa.graph.TransportGraph;
import com.routewise.entity.City;
import com.routewise.entity.Route;
import com.routewise.repository.CityRepository;
import com.routewise.repository.RouteRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RouteOptimizationService {

    private final CityRepository cityRepository;
    private final RouteRepository routeRepository;
    private TransportGraph transportGraph = new TransportGraph();

    public RouteOptimizationService(CityRepository cityRepository, RouteRepository routeRepository) {
        this.cityRepository = cityRepository;
        this.routeRepository = routeRepository;
    }

    @PostConstruct
    public void initGraph() {
        refreshGraph();
    }

    public synchronized void refreshGraph() {
        TransportGraph newGraph = new TransportGraph();
        List<City> cities = cityRepository.findAll();
        for (City c : cities) {
            newGraph.addNode(new GraphNode(c.getId(), c.getName(), c.getCode(), c.getLatitude(), c.getLongitude()));
        }

        List<Route> routes = routeRepository.findByIsActiveTrue();
        for (Route r : routes) {
            double fare = r.getDistanceKm() * 2.2; // Estimated benchmark fare per km
            newGraph.addEdge(
                    r.getSourceCity().getName(),
                    r.getDestinationCity().getName(),
                    r.getDistanceKm(),
                    r.getEstimatedDurationMins(),
                    fare,
                    true // Intercity routes are bidirectional
            );
        }

        this.transportGraph = newGraph;
    }

    public RoutePathResult findShortestDistanceRoute(String fromCity, String toCity) {
        return transportGraph.dijkstra(fromCity, toCity, "DISTANCE");
    }

    public RoutePathResult findFastestDurationRoute(String fromCity, String toCity) {
        return transportGraph.dijkstra(fromCity, toCity, "DURATION");
    }

    public RoutePathResult findFewestTransfersRoute(String fromCity, String toCity) {
        return transportGraph.bfs(fromCity, toCity);
    }

    public List<RoutePathResult> findAllAlternativeRoutes(String fromCity, String toCity) {
        return transportGraph.dfsAllPaths(fromCity, toCity, 5);
    }

    public List<GraphEdge> getMinimumSpanningNetwork() {
        return transportGraph.kruskalMST();
    }

    public RoutePathResult checkNegativeCycles(String fromCity, String toCity) {
        return transportGraph.bellmanFord(fromCity, toCity);
    }

    public Map<String, Object> getNetworkTopology() {
        Map<String, Object> topology = new LinkedHashMap<>();
        topology.put("nodes", transportGraph.getNodes().values());
        topology.put("edges", transportGraph.getAllEdges());
        return topology;
    }
}
