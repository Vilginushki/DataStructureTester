package io.github.vilginushki.structures;

import io.github.vilginushki.DataStructure;

import java.util.TreeSet;

public class TreeStructure<T extends Comparable<T>> implements DataStructure<T> {
    private TreeSet<T> tree;

    public TreeStructure() {
        this.tree = new TreeSet<>();
    }

    @Override
    public void insert(T value) {
        tree.add(value);
    }

    @Override
    public boolean search(T value) {
        return tree.contains(value);
    }

    @Override
    public void remove(T value) {
        tree.remove(value);
    }
}
