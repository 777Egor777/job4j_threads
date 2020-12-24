package ru.job4j.concurrent;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 24.12.2020
 */
public class ConcurrentOutput {
    public static void main(String[] args) {
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        second.start();
        Thread another = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        another.start();
        System.out.println(Thread.currentThread().getName());
    }
}
