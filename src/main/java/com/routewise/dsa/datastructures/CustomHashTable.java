package com.routewise.dsa.datastructures;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom Hash Table with Separate Chaining for collision resolution.
 * Demonstrates manual hash computation, prime-capacity bucket arrays, and collision handling.
 */
public class CustomHashTable<K, V> {

    public static class Entry<K, V> {
        public final K key;
        public V value;
        public Entry<K, V> next;

        public Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private Entry<K, V>[] buckets;
    private int capacity;
    private int size;
    private final double loadFactorThreshold;

    @SuppressWarnings("unchecked")
    public CustomHashTable(int initialCapacity, double loadFactorThreshold) {
        this.capacity = initialCapacity > 0 ? initialCapacity : 16;
        this.loadFactorThreshold = loadFactorThreshold > 0 ? loadFactorThreshold : 0.75;
        this.buckets = (Entry<K, V>[]) new Entry[capacity];
        this.size = 0;
    }

    public CustomHashTable() {
        this(16, 0.75);
    }

    private int hash(K key) {
        if (key == null) return 0;
        int h = key.hashCode();
        // Spread higher bits down
        h = h ^ (h >>> 16);
        return Math.abs(h % capacity);
    }

    public void put(K key, V value) {
        if (key == null) return;
        if ((double) (size + 1) / capacity > loadFactorThreshold) {
            resize();
        }

        int index = hash(key);
        Entry<K, V> head = buckets[index];
        Entry<K, V> curr = head;

        while (curr != null) {
            if (curr.key.equals(key)) {
                curr.value = value;
                return;
            }
            curr = curr.next;
        }

        Entry<K, V> newEntry = new Entry<>(key, value, head);
        buckets[index] = newEntry;
        size++;
    }

    public V get(K key) {
        if (key == null) return null;
        int index = hash(key);
        Entry<K, V> curr = buckets[index];
        while (curr != null) {
            if (curr.key.equals(key)) {
                return curr.value;
            }
            curr = curr.next;
        }
        return null;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public V remove(K key) {
        if (key == null) return null;
        int index = hash(key);
        Entry<K, V> curr = buckets[index];
        Entry<K, V> prev = null;

        while (curr != null) {
            if (curr.key.equals(key)) {
                if (prev == null) {
                    buckets[index] = curr.next;
                } else {
                    prev.next = curr.next;
                }
                size--;
                return curr.value;
            }
            prev = curr;
            curr = curr.next;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        int newCapacity = capacity * 2;
        Entry<K, V>[] newBuckets = (Entry<K, V>[]) new Entry[newCapacity];
        int oldCapacity = capacity;
        capacity = newCapacity;

        for (int i = 0; i < oldCapacity; i++) {
            Entry<K, V> curr = buckets[i];
            while (curr != null) {
                Entry<K, V> next = curr.next;
                int newIndex = hash(curr.key);
                curr.next = newBuckets[newIndex];
                newBuckets[newIndex] = curr;
                curr = next;
            }
        }
        this.buckets = newBuckets;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCollisionCount() {
        int collisions = 0;
        for (Entry<K, V> head : buckets) {
            if (head != null) {
                int chainLength = 0;
                Entry<K, V> curr = head;
                while (curr != null) {
                    chainLength++;
                    curr = curr.next;
                }
                if (chainLength > 1) {
                    collisions += (chainLength - 1);
                }
            }
        }
        return collisions;
    }

    public List<V> values() {
        List<V> list = new ArrayList<>(size);
        for (Entry<K, V> head : buckets) {
            Entry<K, V> curr = head;
            while (curr != null) {
                list.add(curr.value);
                curr = curr.next;
            }
        }
        return list;
    }
}
