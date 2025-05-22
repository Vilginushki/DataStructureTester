package io.github.vilginushki.structures.implementations;

public class Node<T> {
    T value;
    Node<T>[] forward;

    @SuppressWarnings("unchecked")
    public Node(T value, int level) {
        this.value = value;
        this.forward = new Node[level + 1];
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Node<T>[] getForward() {
        return forward;
    }

    public void setForward(Node<T>[] forward) {
        this.forward = forward;
    }
}
