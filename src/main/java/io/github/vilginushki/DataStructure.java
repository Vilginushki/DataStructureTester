package io.github.vilginushki;

public interface DataStructure<T> {

    void insert(T value);

    boolean search(T value);

    void remove(T value);

}
