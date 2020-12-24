package ru.job4j.concurrent;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 24.12.2020
 */
public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> print("Thread " + Thread.currentThread().getName() +  " created.")
        );
        Thread second = new Thread(
                () -> print("Thread " + Thread.currentThread().getName() +  " created.")
        );
        print(first.getName() + " " + first.getState());
        print(second.getName() + " " + second.getState());
        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED
            || second.getState() != Thread.State.TERMINATED) {
            print(first.getName() + " " + first.getState());
            print(second.getName() + " " + second.getState());
        }
        print(first.getName() + " " + first.getState());
        print(second.getName() + " " + second.getState());
        print("Work completed.");
    }

    public static void print(Object o) {
        System.out.println(o.toString());
    }
}
