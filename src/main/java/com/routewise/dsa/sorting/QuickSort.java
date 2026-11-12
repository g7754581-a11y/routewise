package com.routewise.dsa.sorting;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class QuickSort {

    private long comparisons = 0;
    private long swaps = 0;

    public SortMetrics sort(int[] originalArray) {
        if (originalArray == null) {
            return new SortMetrics("Quick Sort", 0, 0, 0, 0, "O(n log n)", "O(n log n)", "O(n²)", "O(log n)", false, new int[0]);
        }
        int[] arr = Arrays.copyOf(originalArray, originalArray.length);
        comparisons = 0;
        swaps = 0;

        long startTime = System.nanoTime();
        if (arr.length > 1) {
            quickSort(arr, 0, arr.length - 1);
        }
        long duration = System.nanoTime() - startTime;

        return new SortMetrics(
                "Quick Sort",
                arr.length,
                comparisons,
                swaps,
                duration,
                "O(n log n)",
                "O(n log n)",
                "O(n²)",
                "O(log n)",
                false,
                arr
        );
    }

    private void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private int partition(int[] arr, int low, int high) {
        // Median-of-three pivot selection to prevent worst-case on sorted inputs
        int mid = low + (high - low) / 2;
        int pivotIndex = medianOfThree(arr, low, mid, high);
        swap(arr, pivotIndex, high);

        int pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            comparisons++;
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private int medianOfThree(int[] arr, int a, int b, int c) {
        comparisons += 2;
        if ((arr[a] > arr[b]) ^ (arr[a] > arr[c])) return a;
        else if ((arr[b] < arr[a]) ^ (arr[b] < arr[c])) return b;
        else return c;
    }

    private void swap(int[] arr, int i, int j) {
        if (i != j) {
            swaps++;
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    // Generic QuickSort for domain entities (Bus schedules, fares, etc.)
    public static <T> void sort(List<T> list, Comparator<? super T> comparator) {
        if (list == null || list.size() <= 1) return;
        quickSortGeneric(list, 0, list.size() - 1, comparator);
    }

    private static <T> void quickSortGeneric(List<T> list, int low, int high, Comparator<? super T> comparator) {
        if (low < high) {
            int pi = partitionGeneric(list, low, high, comparator);
            quickSortGeneric(list, low, pi - 1, comparator);
            quickSortGeneric(list, pi + 1, high, comparator);
        }
    }

    private static <T> int partitionGeneric(List<T> list, int low, int high, Comparator<? super T> comparator) {
        T pivot = list.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (comparator.compare(list.get(j), pivot) <= 0) {
                i++;
                T temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
            }
        }
        T temp = list.get(i + 1);
        list.set(i + 1, list.get(high));
        list.set(high, temp);
        return i + 1;
    }
}
