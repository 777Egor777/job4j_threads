package ru.job4j.storage;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 26.12.2020
 */
public interface Storage<T> {
    boolean add(T t);
    boolean update(T t);
    boolean delete(T t);
}
