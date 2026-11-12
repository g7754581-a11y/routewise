package com.routewise.dsa.dp;

import java.util.ArrayList;
import java.util.List;

public class KnapsackOptimizer {

    public static class Item {
        private String name;
        private int weight; // seats / kg required
        private double value; // revenue in INR

        public Item(String name, int weight, double value) {
            this.name = name;
            this.weight = weight;
            this.value = value;
        }

        public String getName() { return name; }
        public int getWeight() { return weight; }
        public double getValue() { return value; }
    }

    public static class KnapsackResult {
        private double maxValue;
        private int capacityUsed;
        private List<Item> selectedItems;
        private double[][] dpTable;
        private String algorithm;
        private String timeComplexity;
        private String spaceComplexity;

        public KnapsackResult(double maxValue, int capacityUsed, List<Item> selectedItems,
                              double[][] dpTable, String algorithm, String timeComplexity, String spaceComplexity) {
            this.maxValue = maxValue;
            this.capacityUsed = capacityUsed;
            this.selectedItems = selectedItems;
            this.dpTable = dpTable;
            this.algorithm = algorithm;
            this.timeComplexity = timeComplexity;
            this.spaceComplexity = spaceComplexity;
        }

        public double getMaxValue() { return maxValue; }
        public int getCapacityUsed() { return capacityUsed; }
        public List<Item> getSelectedItems() { return selectedItems; }
        public double[][] getDpTable() { return dpTable; }
        public String getAlgorithm() { return algorithm; }
        public String getTimeComplexity() { return timeComplexity; }
        public String getSpaceComplexity() { return spaceComplexity; }
    }

    /**
     * 0/1 Knapsack: Choose optimal group/cargo bookings where each can be taken at most once.
     */
    public static KnapsackResult solve01Knapsack(List<Item> items, int capacity) {
        int n = items.size();
        double[][] dp = new double[n + 1][capacity + 1];

        for (int i = 1; i <= n; i++) {
            Item item = items.get(i - 1);
            int w = item.getWeight();
            double v = item.getValue();

            for (int c = 0; c <= capacity; c++) {
                if (w <= c) {
                    dp[i][c] = Math.max(dp[i - 1][c], dp[i - 1][c - w] + v);
                } else {
                    dp[i][c] = dp[i - 1][c];
                }
            }
        }

        // Backtrack to find selected items
        List<Item> selected = new ArrayList<>();
        int currCap = capacity;
        for (int i = n; i > 0 && currCap > 0; i--) {
            if (dp[i][currCap] != dp[i - 1][currCap]) {
                Item item = items.get(i - 1);
                selected.add(item);
                currCap -= item.getWeight();
            }
        }

        int capacityUsed = capacity - currCap;
        return new KnapsackResult(
                Math.round(dp[n][capacity] * 100.0) / 100.0,
                capacityUsed,
                selected,
                dp,
                "0/1 Knapsack Optimization",
                "O(n · W)",
                "O(n · W)"
        );
    }

    /**
     * Unbounded Knapsack: Choose optimal items where each can be chosen repeatedly.
     */
    public static KnapsackResult solveUnboundedKnapsack(List<Item> items, int capacity) {
        int n = items.size();
        double[] dp = new double[capacity + 1];
        int[] lastItem = new int[capacity + 1];

        for (int c = 1; c <= capacity; c++) {
            lastItem[c] = -1;
            for (int i = 0; i < n; i++) {
                Item item = items.get(i);
                int w = item.getWeight();
                double v = item.getValue();
                if (w <= c && dp[c - w] + v > dp[c]) {
                    dp[c] = dp[c - w] + v;
                    lastItem[c] = i;
                }
            }
        }

        // Reconstruct solution
        List<Item> selected = new ArrayList<>();
        int curr = capacity;
        while (curr > 0 && lastItem[curr] != -1) {
            Item item = items.get(lastItem[curr]);
            selected.add(item);
            curr -= item.getWeight();
        }

        // Form 2D representation for visualization
        double[][] dpView = new double[1][capacity + 1];
        dpView[0] = dp;

        return new KnapsackResult(
                Math.round(dp[capacity] * 100.0) / 100.0,
                capacity - curr,
                selected,
                dpView,
                "Unbounded Knapsack Optimization",
                "O(n · W)",
                "O(W)"
        );
    }
}
