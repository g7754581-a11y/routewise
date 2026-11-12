package com.routewise.dsa.sorting;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class HeapSort {

    private long comparisons = 0;
    private long swaps = 0;

    public SortMetrics sort(int[] originalArray) {
        if (originalArray == null) {
            return new SortMetrics("Heap Sort", 0, 0, 0, 0, "O(n log n)", "O(n log n)", "O(n log n)", "O(1)", false, new int[0]);
        }
        int[] arr = Arrays.copyOf(originalArray, originalArray.length);
        comparisons = 0;
        swaps = 0;

        long startTime = System.nanoTime();
        int n = arr.length;

        // Build max heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // Extract elements from heap one by one
        for (int i = n - 1; i > 0; i--) {
            swap(arr, 0, i);
            heapify(arr, i, 0);
        }
        long duration = System.nanoTime() - startTime;

        return new SortMetrics(
                "Heap Sort",
                arr.length,
                comparisons,
                swaps,
                duration,
                "O(n log n)",
                "O(n log n)",
                "O(n log n)",
                "O(1)",
                false,
                arr
        );
    }

    private void heapify(int[] arr, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n) {
            comparisons++;
            if (arr[left] > arr[largest]) {
                largest = left;
            }
        }

        if (right < n) {
            comparisons++;
            if (arr[right] > arr[largest]) {
                largest = right;
            }
        }

        if (largest != i) {
            swap(arr, i, largest);
            heapify(arr, n, largest);
        }
    }

    private void swap(int[] arr, int i, int j) {
        if (i != j) {
            swaps++;
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    // Generic HeapSort for collections
    public static <T> void sort(List<T> list, Comparator<? super T> comparator) {
        if (list == null || list.size() <= 1) return;
        int n = list.size();

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapifyGeneric(list, n, i, comparator);
        }

        for (int i = n - 1; i > 0; i--) {
            T temp = list.get(0);
            list.set(0, list.get(i));
            list.set(i, temp);
            heapifyGeneric(list, i, 0, comparator);
        }
    }

    private static <T> void heapifyGeneric(List<T> list, int n, int i, Comparator<? super T> comparator) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && comparator.compare(list.get(left), list.get(largest)) > 0) {
            largest = left;
        }

        if (right < n && comparator.compare(list.get(right), list.get(largest)) > 0) {
            largest = right;
        }

        if (largest != i) {
            T swap = list.get(i);
            list.set(i, list.get(largest));
            list.set(largest, swap);
            heapifyGeneric(list, n, largest, comparator);
        }
    }
}
