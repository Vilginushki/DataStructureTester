package io.github.vilginushki.structures;

import io.github.vilginushki.DataStructure;
import io.github.vilginushki.structures.implementations.SkipGraph;

public class SkipGraphStructure<T extends Comparable<T>> implements DataStructure<T> {
    private SkipGraph<T> skipGraph;

    public SkipGraphStructure() {
        this.skipGraph = new SkipGraph<>();
    }

    @Override
    public void insert(T value) {
        skipGraph.insert(value);
    }

    @Override
    public boolean search(T value) {
        return skipGraph.search(value);
    }

    @Override
    public void remove(T value) {
        skipGraph.remove(value);
    }
}
