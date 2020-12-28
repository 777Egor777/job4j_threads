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
}
