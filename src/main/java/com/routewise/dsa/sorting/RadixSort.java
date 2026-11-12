package com.routewise.dsa.sorting;

import java.util.Arrays;

public class RadixSort {

    public SortMetrics sort(int[] originalArray) {
        if (originalArray == null || originalArray.length == 0) {
            return new SortMetrics("Radix Sort", 0, 0, 0, 0, "O(d·(n + k))", "O(d·(n + k))", "O(d·(n + k))", "O(n + k)", true, new int[0]);
        }

        int[] arr = Arrays.copyOf(originalArray, originalArray.length);
        long startTime = System.nanoTime();

        // Handle negative numbers if any, or find maximum value
        int max = arr[0];
        for (int val : arr) {
            if (val > max) max = val;
        }

        long moves = 0;
        for (int exp = 1; max / exp > 0; exp *= 10) {
            moves += countSortByDigit(arr, exp);
        }

        long duration = System.nanoTime() - startTime;

        return new SortMetrics(
                "Radix Sort",
                arr.length,
                0, // Radix sort is non-comparative
                moves,
                duration,
                "O(d·(n + k))",
                "O(d·(n + k))",
                "O(d·(n + k))",
                "O(n + k)",
                true,
                arr
        );
    }

    private long countSortByDigit(int[] arr, int exp) {
        int n = arr.length;
        int[] output = new int[n];
        int[] count = new int[10];
        long moves = 0;

        for (int i = 0; i < n; i++) {
            int digit = (arr[i] / exp) % 10;
            count[digit]++;
        }

        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            int digit = (arr[i] / exp) % 10;
            output[count[digit] - 1] = arr[i];
            count[digit]--;
            moves++;
        }

        for (int i = 0; i < n; i++) {
            arr[i] = output[i];
            moves++;
        }

        return moves;
    }
}
