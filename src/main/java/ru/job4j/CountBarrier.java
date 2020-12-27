package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 27.12.2020
 */
@ThreadSafe
public final class CountBarrier {
    private final Object monitor = this;
    private final int total;
    @GuardedBy("monitor")
    private int count = 0;

    public CountBarrier(int total) {
        this.total = total;
    }

    public final void count() {
        synchronized (monitor) {
            count++;
            if (count == total) {
                monitor.notifyAll();
            }
        }
    }

    public final void await() {
        synchronized (monitor) {
            while (count != total) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
