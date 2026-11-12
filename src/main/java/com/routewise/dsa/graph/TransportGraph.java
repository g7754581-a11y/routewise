package com.routewise.dsa.graph;

import com.routewise.dsa.datastructures.DisjointSetUnion;

import java.util.*;

public class TransportGraph {

    private final Map<String, GraphNode> nodes = new LinkedHashMap<>();
    private final Map<String, List<GraphEdge>> adjacencyList = new LinkedHashMap<>();
    private final List<GraphEdge> allEdges = new ArrayList<>();

    public void addNode(GraphNode node) {
        nodes.put(node.getName().toLowerCase(), node);
        adjacencyList.putIfAbsent(node.getName().toLowerCase(), new ArrayList<>());
    }

    public void addEdge(String from, String to, double distanceKm, int durationMins, double baseFare, boolean bidirectional) {
        String u = from.toLowerCase();
        String v = to.toLowerCase();

        adjacencyList.putIfAbsent(u, new ArrayList<>());
        adjacencyList.putIfAbsent(v, new ArrayList<>());

        GraphEdge edge1 = new GraphEdge(from, to, distanceKm, durationMins, baseFare);
        adjacencyList.get(u).add(edge1);
        allEdges.add(edge1);

        if (bidirectional) {
            GraphEdge edge2 = new GraphEdge(to, from, distanceKm, durationMins, baseFare);
            adjacencyList.get(v).add(edge2);
            allEdges.add(edge2);
        }
    }

    public Map<String, GraphNode> getNodes() {
        return nodes;
    }

    public Map<String, List<GraphEdge>> getAdjacencyList() {
        return adjacencyList;
    }

    public List<GraphEdge> getAllEdges() {
        return allEdges;
    }

    // =========================================================================
    // A. DIJKSTRA'S ALGORITHM (Shortest path by distance or duration)
    // =========================================================================
    public RoutePathResult dijkstra(String sourceCity, String destCity, String metric) {
        String src = sourceCity.toLowerCase().trim();
        String dst = destCity.toLowerCase().trim();

        if (!adjacencyList.containsKey(src) || !adjacencyList.containsKey(dst)) {
            return new RoutePathResult(Collections.emptyList(), 0, 0, 0, 0, "Dijkstra", false, "City not found in network");
        }

        Map<String, Double> distances = new HashMap<>();
        Map<String, String> predecessors = new HashMap<>();
        Map<String, GraphEdge> bestEdgeTo = new HashMap<>();

        for (String node : adjacencyList.keySet()) {
            distances.put(node, Double.POSITIVE_INFINITY);
        }
        distances.put(src, 0.0);

        PriorityQueue<String> pq = new PriorityQueue<>(Comparator.comparingDouble(distances::get));
        pq.add(src);

        Set<String> visited = new HashSet<>();

        while (!pq.isEmpty()) {
            String u = pq.poll();
            if (visited.contains(u)) continue;
            visited.add(u);

            if (u.equals(dst)) break;

            for (GraphEdge edge : adjacencyList.getOrDefault(u, Collections.emptyList())) {
                String v = edge.getDestination().toLowerCase();
                if (visited.contains(v)) continue;

                double weight = "DURATION".equalsIgnoreCase(metric) ? edge.getDurationMins() : edge.getDistanceKm();
                double newDist = distances.get(u) + weight;

                if (newDist < distances.get(v)) {
                    distances.put(v, newDist);
                    predecessors.put(v, u);
                    bestEdgeTo.put(v, edge);
                    pq.remove(v);
                    pq.add(v);
                }
            }
        }

        if (!predecessors.containsKey(dst) && !src.equals(dst)) {
            return new RoutePathResult(Collections.emptyList(), 0, 0, 0, 0, "Dijkstra (" + metric + ")", false, "No route available between selected cities");
        }

        List<String> path = new ArrayList<>();
        String curr = dst;
        double totalDist = 0;
        int totalDur = 0;
        double totalFare = 0;

        while (curr != null) {
            GraphNode gn = nodes.get(curr);
            path.add(0, gn != null ? gn.getName() : curr);
            GraphEdge edge = bestEdgeTo.get(curr);
            if (edge != null) {
                totalDist += edge.getDistanceKm();
                totalDur += edge.getDurationMins();
                totalFare += edge.getBaseFare();
            }
            curr = predecessors.get(curr);
        }

        int intermediateStops = Math.max(0, path.size() - 2);
        return new RoutePathResult(
                path,
                Math.round(totalDist * 10.0) / 10.0,
                totalDur,
                Math.round(totalFare * 10.0) / 10.0,
                intermediateStops,
                "Dijkstra (" + metric + ")",
                true,
                "Optimal path found with " + intermediateStops + " transfer point(s)"
        );
    }

    // =========================================================================
    // B. BREADTH-FIRST SEARCH (Fewest intermediate stops / minimum hops)
    // =========================================================================
    public RoutePathResult bfs(String sourceCity, String destCity) {
        String src = sourceCity.toLowerCase().trim();
        String dst = destCity.toLowerCase().trim();

        if (!adjacencyList.containsKey(src) || !adjacencyList.containsKey(dst)) {
            return new RoutePathResult(Collections.emptyList(), 0, 0, 0, 0, "BFS", false, "City not in network");
        }

        Queue<String> queue = new LinkedList<>();
        Map<String, String> parent = new HashMap<>();
        Map<String, GraphEdge> edgeUsed = new HashMap<>();
        Set<String> visited = new HashSet<>();

        queue.add(src);
        visited.add(src);

        boolean found = false;
        while (!queue.isEmpty()) {
            String u = queue.poll();
            if (u.equals(dst)) {
                found = true;
                break;
            }

            for (GraphEdge edge : adjacencyList.getOrDefault(u, Collections.emptyList())) {
                String v = edge.getDestination().toLowerCase();
                if (!visited.contains(v)) {
                    visited.add(v);
                    parent.put(v, u);
                    edgeUsed.put(v, edge);
                    queue.add(v);
                }
            }
        }

        if (!found) {
            return new RoutePathResult(Collections.emptyList(), 0, 0, 0, 0, "BFS (Fewest Hops)", false, "No connected path");
        }

        List<String> path = new ArrayList<>();
        String curr = dst;
        double totalDist = 0;
        int totalDur = 0;
        double totalFare = 0;

        while (curr != null) {
            GraphNode gn = nodes.get(curr);
            path.add(0, gn != null ? gn.getName() : curr);
            GraphEdge edge = edgeUsed.get(curr);
            if (edge != null) {
                totalDist += edge.getDistanceKm();
                totalDur += edge.getDurationMins();
                totalFare += edge.getBaseFare();
            }
            curr = parent.get(curr);
        }

        int stops = Math.max(0, path.size() - 2);
        return new RoutePathResult(
                path,
                Math.round(totalDist * 10.0) / 10.0,
                totalDur,
                Math.round(totalFare * 10.0) / 10.0,
                stops,
                "BFS (Minimum Transfers)",
                true,
                "Path with minimum intermediate hops (" + stops + " stops)"
        );
    }

    // =========================================================================
    // C. DEPTH-FIRST SEARCH (Explore reachable destinations & all paths)
    // =========================================================================
    public List<RoutePathResult> dfsAllPaths(String sourceCity, String destCity, int maxPaths) {
        String src = sourceCity.toLowerCase().trim();
        String dst = destCity.toLowerCase().trim();

        List<RoutePathResult> results = new ArrayList<>();
        if (!adjacencyList.containsKey(src) || !adjacencyList.containsKey(dst)) {
            return results;
        }

        List<String> currentPath = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        dfsHelper(src, dst, visited, currentPath, 0, 0, 0, results, maxPaths);
        return results;
    }

    private void dfsHelper(String u, String dst, Set<String> visited, List<String> currentPath,
                           double dist, int dur, double fare, List<RoutePathResult> results, int maxPaths) {
        if (results.size() >= maxPaths) return;

        visited.add(u);
        GraphNode gn = nodes.get(u);
        currentPath.add(gn != null ? gn.getName() : u);

        if (u.equals(dst)) {
            int stops = Math.max(0, currentPath.size() - 2);
            results.add(new RoutePathResult(
                    new ArrayList<>(currentPath),
                    Math.round(dist * 10.0) / 10.0,
                    dur,
                    Math.round(fare * 10.0) / 10.0,
                    stops,
                    "DFS Path Exploration",
                    true,
                    "Alternative Route"
            ));
        } else {
            for (GraphEdge edge : adjacencyList.getOrDefault(u, Collections.emptyList())) {
                String v = edge.getDestination().toLowerCase();
                if (!visited.contains(v)) {
                    dfsHelper(v, dst, visited, currentPath, dist + edge.getDistanceKm(),
                            dur + edge.getDurationMins(), fare + edge.getBaseFare(), results, maxPaths);
                }
            }
        }

        currentPath.remove(currentPath.size() - 1);
        visited.remove(u);
    }

    // =========================================================================
    // D. PRIM'S MINIMUM SPANNING TREE
    // =========================================================================
    public List<GraphEdge> primMST() {
        List<GraphEdge> mst = new ArrayList<>();
        if (nodes.isEmpty()) return mst;

        Set<String> inMST = new HashSet<>();
        PriorityQueue<GraphEdge> pq = new PriorityQueue<>(Comparator.comparingDouble(GraphEdge::getDistanceKm));

        String startNode = nodes.keySet().iterator().next();
        inMST.add(startNode);

        for (GraphEdge edge : adjacencyList.getOrDefault(startNode, Collections.emptyList())) {
            pq.add(edge);
        }

        while (!pq.isEmpty() && inMST.size() < nodes.size()) {
            GraphEdge edge = pq.poll();
            String v = edge.getDestination().toLowerCase();

            if (inMST.contains(v)) continue;

            inMST.add(v);
            mst.add(edge);

            for (GraphEdge nextEdge : adjacencyList.getOrDefault(v, Collections.emptyList())) {
                if (!inMST.contains(nextEdge.getDestination().toLowerCase())) {
                    pq.add(nextEdge);
                }
            }
        }
        return mst;
    }

    // =========================================================================
    // E. KRUSKAL'S MINIMUM SPANNING TREE (Using Disjoint Set Union)
    // =========================================================================
    public List<GraphEdge> kruskalMST() {
        List<GraphEdge> mst = new ArrayList<>();
        List<GraphEdge> sortedEdges = new ArrayList<>(allEdges);
        Collections.sort(sortedEdges);

        Map<String, Integer> cityIndices = new HashMap<>();
        int idx = 0;
        for (String city : nodes.keySet()) {
            cityIndices.put(city, idx++);
        }

        DisjointSetUnion dsu = new DisjointSetUnion(nodes.size());

        for (GraphEdge edge : sortedEdges) {
            Integer uIdx = cityIndices.get(edge.getSource().toLowerCase());
            Integer vIdx = cityIndices.get(edge.getDestination().toLowerCase());

            if (uIdx != null && vIdx != null) {
                if (dsu.union(uIdx, vIdx)) {
                    mst.add(edge);
                    if (mst.size() == nodes.size() - 1) {
                        break;
                    }
                }
            }
        }
        return mst;
    }

    // =========================================================================
    // F. BELLMAN-FORD (Shortest paths with negative cycle detection)
    // =========================================================================
    public RoutePathResult bellmanFord(String sourceCity, String destCity) {
        String src = sourceCity.toLowerCase().trim();
        String dst = destCity.toLowerCase().trim();

        if (!nodes.containsKey(src) || !nodes.containsKey(dst)) {
            return new RoutePathResult(Collections.emptyList(), 0, 0, 0, 0, "Bellman-Ford", false, "City not found");
        }

        Map<String, Double> dist = new HashMap<>();
        Map<String, String> prev = new HashMap<>();
        Map<String, GraphEdge> edgeTo = new HashMap<>();

        for (String city : nodes.keySet()) {
            dist.put(city, Double.POSITIVE_INFINITY);
        }
        dist.put(src, 0.0);

        int V = nodes.size();
        // Relax edges V - 1 times
        for (int i = 1; i <= V - 1; i++) {
            for (GraphEdge edge : allEdges) {
                String u = edge.getSource().toLowerCase();
                String v = edge.getDestination().toLowerCase();
                if (dist.get(u) != Double.POSITIVE_INFINITY && dist.get(u) + edge.getDistanceKm() < dist.get(v)) {
                    dist.put(v, dist.get(u) + edge.getDistanceKm());
                    prev.put(v, u);
                    edgeTo.put(v, edge);
                }
            }
        }

        // Check for negative-weight cycles
        for (GraphEdge edge : allEdges) {
            String u = edge.getSource().toLowerCase();
            String v = edge.getDestination().toLowerCase();
            if (dist.get(u) != Double.POSITIVE_INFINITY && dist.get(u) + edge.getDistanceKm() < dist.get(v)) {
                return new RoutePathResult(Collections.emptyList(), 0, 0, 0, 0, "Bellman-Ford", false, "Graph contains a negative-weight cycle!");
            }
        }

        if (dist.get(dst) == Double.POSITIVE_INFINITY) {
            return new RoutePathResult(Collections.emptyList(), 0, 0, 0, 0, "Bellman-Ford", false, "Unreachable destination");
        }

        List<String> path = new ArrayList<>();
        String curr = dst;
        double totalDist = 0;
        int totalDur = 0;
        double totalFare = 0;

        while (curr != null) {
            GraphNode gn = nodes.get(curr);
            path.add(0, gn != null ? gn.getName() : curr);
            GraphEdge edge = edgeTo.get(curr);
            if (edge != null) {
                totalDist += edge.getDistanceKm();
                totalDur += edge.getDurationMins();
                totalFare += edge.getBaseFare();
            }
            curr = prev.get(curr);
        }

        int stops = Math.max(0, path.size() - 2);
        return new RoutePathResult(
                path,
                Math.round(totalDist * 10.0) / 10.0,
                totalDur,
                Math.round(totalFare * 10.0) / 10.0,
                stops,
                "Bellman-Ford Shortest Path",
                true,
                "Verified no negative cycles in transport network"
        );
    }
}
