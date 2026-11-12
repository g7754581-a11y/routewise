package com.routewise.dsa.datastructures;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Custom Binary Max-Heap based Priority Queue.
 * Elements with higher priority value are dequeued first.
 */
public class CustomPriorityQueue<T> {

    private final List<T> heap;
    private final Comparator<? super T> comparator;

    public CustomPriorityQueue(Comparator<? super T> comparator) {
        this.heap = new ArrayList<>();
        this.comparator = comparator;
    }

    public void insert(T item) {
        heap.add(item);
        siftUp(heap.size() - 1);
    }

    public T extractMax() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority Queue is empty");
        }
        T max = heap.get(0);
        T last = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, last);
            siftDown(0);
        }
        return max;
    }

    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority Queue is empty");
        }
        return heap.get(0);
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public int size() {
        return heap.size();
    }

    public List<T> toList() {
        return new ArrayList<>(heap);
    }

    private void siftUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (comparator.compare(heap.get(index), heap.get(parentIndex)) > 0) {
                swap(index, parentIndex);
                index = parentIndex;
            } else {
                break;
            }
        }
    }

    private void siftDown(int index) {
        int size = heap.size();
        while (true) {
            int largest = index;
            int left = 2 * index + 1;
            int right = 2 * index + 2;

            if (left < size && comparator.compare(heap.get(left), heap.get(largest)) > 0) {
                largest = left;
            }
            if (right < size && comparator.compare(heap.get(right), heap.get(largest)) > 0) {
                largest = right;
            }
            if (largest != index) {
                swap(index, largest);
                index = largest;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j) {
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
}
