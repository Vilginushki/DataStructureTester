package io.github.vilginushki.structures.implementations;

public class SkipGraph<T extends Comparable<T>> {
    private static final int MAX_LEVEL = 16;
    private Node<T> header;
    private int level;

    public SkipGraph() {
        this.header = new Node<>(null, MAX_LEVEL);
        this.level = 0;
    }

    private Node<T>[] findUpdateNodes(T value) {
        Node<T>[] update = new Node[MAX_LEVEL + 1];
        Node<T> current = header;

        for (int i = level; i >= 0; i--) {
            while (current.getForward()[i] != null && current.getForward()[i].getValue().compareTo(value) < 0) {
                current = current.getForward()[i];
            }
            update[i] = current;
        }
        return update;
    }

    public void insert(T value) {
        Node<T>[] update = findUpdateNodes(value);
        Node<T> current = update[0].getForward()[0];

        if (current == null || !current.getValue().equals(value)) {
            int newLevel = randomLevel();
            if (newLevel > level) {
                for (int i = level + 1; i <= newLevel; i++) {
                    update[i] = header;
                }
                level = newLevel;
            }

            Node<T> newNode = new Node<>(value, newLevel);
            for (int i = 0; i <= newLevel; i++) {
                newNode.getForward()[i] = update[i].getForward()[i];
                update[i].getForward()[i] = newNode;
            }
        }
    }

    public boolean search(T value) {
        Node<T>[] update = findUpdateNodes(value);
        Node<T> current = update[0].getForward()[0];
        return current != null && current.getValue().equals(value);
    }

    public void remove(T value) {
        Node<T>[] update = findUpdateNodes(value);
        Node<T> current = update[0].getForward()[0];

        if (current != null && current.getValue().equals(value)) {
            for (int i = 0; i <= level; i++) {
                if (update[i].getForward()[i] != current) {
                    break;
                }
                update[i].getForward()[i] = current.getForward()[i];
            }

            while (level > 0 && header.getForward()[level] == null) {
                level--;
            }
        }
    }

    private int randomLevel() {
        int newLevel = 0;
        while (Math.random() < 0.5 && newLevel < MAX_LEVEL) {
            newLevel++;
        }
        return newLevel;
    }
}