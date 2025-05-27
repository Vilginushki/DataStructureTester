package io.github.vilginushki.structures;

import io.github.vilginushki.DataStructure;

import java.util.HashSet;

public class HashSetStructure<T extends Comparable<T>> implements DataStructure<T> {
    private final HashSet<T> hashSet;

    public HashSetStructure() {
        this.hashSet = new HashSet<>();
    }

    @Override
    public void insert(T value) {
        hashSet.add(value);
    }

    @Override
    public boolean search(T value) {
        return hashSet.contains(value);
    }

    @Override
    public void remove(T value) {
        hashSet.remove(value);
    }
}
