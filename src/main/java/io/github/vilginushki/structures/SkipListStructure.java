package io.github.vilginushki.structures;

import io.github.vilginushki.DataStructure;
import io.github.vilginushki.structures.implementations.SkipList;

public class SkipListStructure<T extends Comparable<T>> implements DataStructure<T> {
    private SkipList<T> skipList;

    public SkipListStructure() {
        this.skipList = new SkipList<>();
    }

    @Override
    public void insert(T value) {
        skipList.insert(value);
    }

    @Override
    public boolean search(T value) {
        return skipList.search(value);
    }

    @Override
    public void remove(T value) {
        skipList.remove(value);
    }
}
