package io.github.vilginushki.structures;

import io.github.vilginushki.DataStructure;

import java.util.HashMap;

public class HashTableStructure<T extends Comparable<T>> implements DataStructure<T> {
    private final HashMap<T, T> hashTable;

    public HashTableStructure() {
        this.hashTable = new HashMap<>();
    }

    @Override
    public void insert(T value) {
        hashTable.put(value, value);
    }

    @Override
    public boolean search(T value) {
        return hashTable.containsKey(value);
    }

    @Override
    public void remove(T value) {
        hashTable.remove(value);
    }
}
