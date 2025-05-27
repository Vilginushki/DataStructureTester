package io.github.vilginushki.structures;

import io.github.vilginushki.DataStructure;

import java.util.SortedSet;
import java.util.TreeSet;

public class SortedSetStructure<T extends Comparable<T>> implements DataStructure<T> {
    private final SortedSet<T> sortedSet;

    public SortedSetStructure() {
        this.sortedSet = new TreeSet<>();
    }

    @Override
    public void insert(T value) {
        sortedSet.add(value);
    }

    @Override
    public boolean search(T value) {
        return sortedSet.contains(value);
    }

    @Override
    public void remove(T value) {
        sortedSet.remove(value);
    }
}
