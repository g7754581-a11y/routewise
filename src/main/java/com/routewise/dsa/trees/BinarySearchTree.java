package com.routewise.dsa.trees;

import java.util.ArrayList;
import java.util.List;

public class BinarySearchTree<K extends Comparable<K>, V> {

    public static class Node<K, V> {
        public K key;
        public V value;
        public Node<K, V> left;
        public Node<K, V> right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    private Node<K, V> root;
    private int size;

    public BinarySearchTree() {
        this.root = null;
        this.size = 0;
    }

    public void insert(K key, V value) {
        root = insertRec(root, key, value);
    }

    private Node<K, V> insertRec(Node<K, V> root, K key, V value) {
        if (root == null) {
            size++;
            return new Node<>(key, value);
        }
        int cmp = key.compareTo(root.key);
        if (cmp < 0) {
            root.left = insertRec(root.left, key, value);
        } else if (cmp > 0) {
            root.right = insertRec(root.right, key, value);
        } else {
            root.value = value; // update
        }
        return root;
    }

    public V search(K key) {
        Node<K, V> curr = root;
        while (curr != null) {
            int cmp = key.compareTo(curr.key);
            if (cmp < 0) curr = curr.left;
            else if (cmp > 0) curr = curr.right;
            else return curr.value;
        }
        return null;
    }

    public boolean contains(K key) {
        return search(key) != null;
    }

    public void delete(K key) {
        root = deleteRec(root, key);
    }

    private Node<K, V> deleteRec(Node<K, V> root, K key) {
        if (root == null) return null;

        int cmp = key.compareTo(root.key);
        if (cmp < 0) {
            root.left = deleteRec(root.left, key);
        } else if (cmp > 0) {
            root.right = deleteRec(root.right, key);
        } else {
            // Node with only one child or no child
            if (root.left == null) {
                size--;
                return root.right;
            } else if (root.right == null) {
                size--;
                return root.left;
            }

            // Node with two children: Get the inorder successor
            Node<K, V> successor = minValueNode(root.right);
            root.key = successor.key;
            root.value = successor.value;
            root.right = deleteRec(root.right, successor.key);
        }
        return root;
    }

    private Node<K, V> minValueNode(Node<K, V> node) {
        Node<K, V> current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    public List<V> inorder() {
        List<V> result = new ArrayList<>();
        inorderRec(root, result);
        return result;
    }

    private void inorderRec(Node<K, V> node, List<V> result) {
        if (node != null) {
            inorderRec(node.left, result);
            result.add(node.value);
            inorderRec(node.right, result);
        }
    }

    public List<V> preorder() {
        List<V> result = new ArrayList<>();
        preorderRec(root, result);
        return result;
    }

    private void preorderRec(Node<K, V> node, List<V> result) {
        if (node != null) {
            result.add(node.value);
            preorderRec(node.left, result);
            preorderRec(node.right, result);
        }
    }

    public List<V> postorder() {
        List<V> result = new ArrayList<>();
        postorderRec(root, result);
        return result;
    }

    private void postorderRec(Node<K, V> node, List<V> result) {
        if (node != null) {
            postorderRec(node.left, result);
            postorderRec(node.right, result);
            result.add(node.value);
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return root == null;
    }
}
