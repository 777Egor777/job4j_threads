package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 28.12.2020
 */
@ThreadSafe
public class CASCount {
    /*
    private final AtomicReference<AtomicInteger> count = new AtomicReference<>(new AtomicInteger(0));

    public void increment() {
        AtomicInteger currentValue;
        AtomicInteger newValue = new AtomicInteger();
        do {
            currentValue = count.get();
            newValue.set(currentValue.get());
            newValue.incrementAndGet();
        } while (!count.compareAndSet(currentValue, newValue));
    }

    public int get() {
        AtomicInteger currentValue;
        do {
            currentValue = count.get();
        } while (!count.compareAndSet(currentValue, currentValue));
        return currentValue.get();
    }
     */
    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        count.incrementAndGet();
    }

    public int get() {
        return count.get();
    }
/*
    public static void main(String[] args) {
        CASCount count = new CASCount();
        Thread threadA = new Thread(
                () -> {
                    for (int i = 0; i < 50; ++i) {
                        count.increment();
                    }
                }
        );
        Thread threadB = new Thread(
                () -> {
                    for (int i = 0; i < 50; ++i) {
                        count.increment();
                    }
                }
        );
        Thread threadC = new Thread(
                () -> {
                    for (int i = 0; i < 50; ++i) {
                        count.increment();
                    }
                }
        );
        threadA.start();
        threadB.start();
        threadC.start();
        try {
            threadA.join();
            threadB.join();
            threadC.join();
        } catch (InterruptedException iex) {
            iex.printStackTrace();
        }
        System.out.println(count.get());
    }

 */
    public static void main(String[] args) {
        CASCount count = new CASCount();
        final int n = 150;
        Thread[] thread = new Thread[n];
        for (int i = 0; i < n; ++i) {
            thread[i] = new Thread(
                    count::increment
            );
        }
        for (int i = 0; i < n; ++i) {
            thread[i].start();
        }

        try {
            for (int i = 0; i < n; ++i) {
                thread[i].join();
            }
        } catch (InterruptedException iex) {
            iex.printStackTrace();
        }
        System.out.println(count.get());
    }
}
