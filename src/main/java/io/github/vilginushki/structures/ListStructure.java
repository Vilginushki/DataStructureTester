package io.github.vilginushki.structures;

import io.github.vilginushki.DataStructure;

import java.util.ArrayList;
import java.util.List;

public class ListStructure<T> implements DataStructure<T> {
    private List<T> list;

    public ListStructure() {
        this.list = new ArrayList<>();
    }

    @Override
    public void insert(T value) {
        list.add(value);
    }

    @Override
    public boolean search(T value) {
        return list.contains(value);
    }

    @Override
    public void remove(T value) {
        list.remove(value);
    }
}
