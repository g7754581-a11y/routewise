package com.routewise.dsa.sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BucketSort {

    public SortMetrics sort(int[] originalArray) {
        if (originalArray == null || originalArray.length == 0) {
            return new SortMetrics("Bucket Sort", 0, 0, 0, 0, "O(n + k)", "O(n + k)", "O(n²)", "O(n · k)", true, new int[0]);
        }

        int[] arr = Arrays.copyOf(originalArray, originalArray.length);
        long startTime = System.nanoTime();

        int min = arr[0];
        int max = arr[0];
        for (int v : arr) {
            if (v < min) min = v;
            if (v > max) max = v;
        }

        if (min == max) {
            long duration = System.nanoTime() - startTime;
            return new SortMetrics("Bucket Sort", arr.length, 0, 0, duration, "O(n + k)", "O(n + k)", "O(n²)", "O(n · k)", true, arr);
        }

        int bucketCount = Math.max(1, (int) Math.sqrt(arr.length));
        List<List<Integer>> buckets = new ArrayList<>(bucketCount);
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }

        double range = (double) (max - min + 1) / bucketCount;
        for (int v : arr) {
            int bucketIdx = (int) ((v - min) / range);
            if (bucketIdx >= bucketCount) bucketIdx = bucketCount - 1;
            buckets.get(bucketIdx).add(v);
        }

        long moves = 0;
        int idx = 0;
        for (List<Integer> bucket : buckets) {
            // Sort each individual bucket using insertion sort
            insertionSort(bucket);
            for (int v : bucket) {
                arr[idx++] = v;
                moves++;
            }
        }

        long duration = System.nanoTime() - startTime;

        return new SortMetrics(
                "Bucket Sort",
                arr.length,
                0,
                moves,
                duration,
                "O(n + k)",
                "O(n + k)",
                "O(n²)",
                "O(n · k)",
                true,
                arr
        );
    }

    private void insertionSort(List<Integer> list) {
        for (int i = 1; i < list.size(); i++) {
            int key = list.get(i);
            int j = i - 1;
            while (j >= 0 && list.get(j) > key) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, key);
        }
    }
}
