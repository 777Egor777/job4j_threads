package ru.job4j.storage;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 26.12.2020
 */
public interface Storage<T> {
    boolean add(T t);
    boolean update(T t1, T t2);
    boolean delete(T t);
}
