package com.routewise.dsa.sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MergeSort {

    private long comparisons = 0;
    private long moves = 0;

    public SortMetrics sort(int[] originalArray) {
        if (originalArray == null) {
            return new SortMetrics("Merge Sort", 0, 0, 0, 0, "O(n log n)", "O(n log n)", "O(n log n)", "O(n)", true, new int[0]);
        }
        int[] arr = Arrays.copyOf(originalArray, originalArray.length);
        comparisons = 0;
        moves = 0;

        long startTime = System.nanoTime();
        if (arr.length > 1) {
            mergeSort(arr, 0, arr.length - 1);
        }
        long duration = System.nanoTime() - startTime;

        return new SortMetrics(
                "Merge Sort",
                arr.length,
                comparisons,
                moves,
                duration,
                "O(n log n)",
                "O(n log n)",
                "O(n log n)",
                "O(n)",
                true,
                arr
        );
    }

    private void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] L = new int[n1];
        int[] R = new int[n2];

        for (int i = 0; i < n1; i++) {
            L[i] = arr[left + i];
            moves++;
        }
        for (int j = 0; j < n2; j++) {
            R[j] = arr[mid + 1 + j];
            moves++;
        }

        int i = 0, j = 0;
        int k = left;
        while (i < n1 && j < n2) {
            comparisons++;
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            moves++;
            k++;
        }

        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
            moves++;
        }

        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
            moves++;
        }
    }

    // Generic MergeSort for lists
    public static <T> void sort(List<T> list, Comparator<? super T> comparator) {
        if (list == null || list.size() <= 1) return;
        List<T> sorted = mergeSortGeneric(list, comparator);
        for (int i = 0; i < list.size(); i++) {
            list.set(i, sorted.get(i));
        }
    }

    private static <T> List<T> mergeSortGeneric(List<T> list, Comparator<? super T> comparator) {
        if (list.size() <= 1) return list;
        int mid = list.size() / 2;
        List<T> left = mergeSortGeneric(new ArrayList<>(list.subList(0, mid)), comparator);
        List<T> right = mergeSortGeneric(new ArrayList<>(list.subList(mid, list.size())), comparator);
        return mergeGeneric(left, right, comparator);
    }

    private static <T> List<T> mergeGeneric(List<T> left, List<T> right, Comparator<? super T> comparator) {
        List<T> result = new ArrayList<>();
        int i = 0, j = 0;
        while (i < left.size() && j < right.size()) {
            if (comparator.compare(left.get(i), right.get(j)) <= 0) {
                result.add(left.get(i));
                i++;
            } else {
                result.add(right.get(j));
                j++;
            }
        }
        while (i < left.size()) result.add(left.get(i++));
        while (j < right.size()) result.add(right.get(j++));
        return result;
    }
}
