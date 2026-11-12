package com.routewise.dsa.datastructures;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

/**
 * Custom LIFO Stack implementation.
 * Used for storing reversible administrative actions and operational history.
 */
public class CustomStack<T> {

    private static class Node<T> {
        T data;
        Node<T> next;
        Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }
    }

    private Node<T> top;
    private int size;

    public CustomStack() {
        this.top = null;
        this.size = 0;
    }

    public void push(T item) {
        top = new Node<>(item, top);
        size++;
    }

    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        T item = top.data;
        top = top.next;
        size--;
        return item;
    }

    public T peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return top.data;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }

    public List<T> toList() {
        List<T> list = new ArrayList<>(size);
        Node<T> curr = top;
        while (curr != null) {
            list.add(curr.data);
            curr = curr.next;
        }
        return list;
    }

    public void clear() {
        top = null;
        size = 0;
    }
}
