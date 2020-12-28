package ru.job4j.nonblock;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 28.12.2020
 */
public interface Cache<T> {
    void add(T t);
    void delete(T t);
    void update(T t);
}
