package io.github.vilginushki.structures;

import io.github.vilginushki.DataStructure;

public class DictionaryStructure<T extends Comparable<T>> implements DataStructure<T> {
    private final java.util.Map<T, T> dictionary;

    public DictionaryStructure() {
        this.dictionary = new java.util.HashMap<>();
    }

    @Override
    public void insert(T value) {
        dictionary.put(value, value);
    }

    @Override
    public boolean search(T value) {
        return dictionary.containsKey(value);
    }

    @Override
    public void remove(T value) {
        dictionary.remove(value);
    }
}
