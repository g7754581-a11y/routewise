package com.routewise.controller;

import com.routewise.dsa.dp.KnapsackOptimizer;
import com.routewise.dsa.sorting.*;
import com.routewise.dto.DashboardMetricsDTO;
import com.routewise.dto.UserDTO;
import com.routewise.entity.Role;
import com.routewise.service.BookingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final BookingService bookingService;

    public AdminController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/metrics")
    public ResponseEntity<?> getMetrics(HttpSession session) {
        if (!isAdmin(session)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "Admin access required"));
        }
        DashboardMetricsDTO metrics = bookingService.getDashboardMetrics();
        return ResponseEntity.ok(metrics);
    }

    @PostMapping("/optimizer/knapsack")
    public ResponseEntity<?> runCapacityOptimizer(@RequestBody Map<String, Object> payload, HttpSession session) {
        if (!isAdmin(session)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "Admin access required"));
        }
        try {
            int capacity = ((Number) payload.getOrDefault("capacity", 40)).intValue();
            String mode = (String) payload.getOrDefault("mode", "01"); // "01" or "unbounded"
            List<Map<String, Object>> itemsRaw = (List<Map<String, Object>>) payload.get("items");

            List<KnapsackOptimizer.Item> items = new ArrayList<>();
            if (itemsRaw != null) {
                for (Map<String, Object> m : itemsRaw) {
                    String name = (String) m.get("name");
                    int weight = ((Number) m.get("weight")).intValue();
                    double value = ((Number) m.get("value")).doubleValue();
                    items.add(new KnapsackOptimizer.Item(name, weight, value));
                }
            } else {
                // Default corporate group booking and cargo packages
                items.add(new KnapsackOptimizer.Item("TechCorp Group (12 seats)", 12, 11500));
                items.add(new KnapsackOptimizer.Item("Medical Conference Delegations (8 seats)", 8, 8200));
                items.add(new KnapsackOptimizer.Item("Express Parcel Cargo Bay (15 units)", 15, 14000));
                items.add(new KnapsackOptimizer.Item("University Tour Contingent (18 seats)", 18, 15500));
                items.add(new KnapsackOptimizer.Item("Wedding Party Reservation (6 seats)", 6, 6800));
                items.add(new KnapsackOptimizer.Item("VIP Executive Suite (4 seats)", 4, 5200));
                items.add(new KnapsackOptimizer.Item("Cold Storage Pharma Cargo (10 units)", 10, 11000));
            }

            KnapsackOptimizer.KnapsackResult result;
            if ("unbounded".equalsIgnoreCase(mode)) {
                result = KnapsackOptimizer.solveUnboundedKnapsack(items, capacity);
            } else {
                result = KnapsackOptimizer.solve01Knapsack(items, capacity);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/sort-benchmark")
    public ResponseEntity<?> runSortBenchmark(@RequestBody Map<String, Object> payload) {
        int size = payload.get("size") != null ? ((Number) payload.get("size")).intValue() : 1000;
        if (size > 100000) size = 100000;

        Random rand = new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(10000);
        }

        QuickSort qs = new QuickSort();
        MergeSort ms = new MergeSort();
        HeapSort hs = new HeapSort();
        RadixSort rs = new RadixSort();
        BucketSort bs = new BucketSort();

        Map<String, Object> results = new LinkedHashMap<>();
        results.put("quickSort", qs.sort(arr));
        results.put("mergeSort", ms.sort(arr));
        results.put("heapSort", hs.sort(arr));
        results.put("radixSort", rs.sort(arr));
        results.put("bucketSort", bs.sort(arr));

        return ResponseEntity.ok(results);
    }

    private boolean isAdmin(HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute(AuthController.SESSION_USER_KEY);
        return user != null && user.getRole() == Role.ROLE_ADMIN;
    }
}
