package io.github.vilginushki.structures;

import io.github.vilginushki.DataStructure;

import java.util.HashSet;
import java.util.Set;

public class SetStructure<T> implements DataStructure<T> {
    private Set<T> set;

    public SetStructure() {
        this.set = new HashSet<>();
    }

    @Override
    public void insert(T value) {
        set.add(value);
    }

    @Override
    public boolean search(T value) {
        return set.contains(value);
    }
}
