package ru.job4j.linked;

import net.jcip.annotations.Immutable;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 25.12.2020
 */
@Immutable
public final class Node<T> {
    private final Node<T> next;
    private final T value;

    public Node(Node<T> next, T value) {
        this.next = next;
        this.value = value;
    }

    public final Node<T> getNext() {
        return next;
    }

    public final T getValue() {
        return value;
    }

    public final synchronized Node<T> setNext(Node<T> next) {
        return new Node<>(next, value);
    }

    public final synchronized Node<T> setValue(T value) {
        return new Node<>(next, value);
    }
}
