package ru.job4j.queue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 27.12.2020
 */
@ThreadSafe
public final class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private boolean canAddFlag = true;
    private boolean canDeleteFlag = false;
    private final int size;

    public SimpleBlockingQueue(int size) {
        this.size = size;
    }

    public final synchronized void offer(T value) {
        checkAdd();
        queue.offer(value);
        canDeleteFlag = true;
        if (queue.size() >= size) {
            canAddFlag = false;
        }
        this.notifyAll();
    }

    public final synchronized T poll() {
        checkDelete();
        T value = queue.poll();
        canAddFlag = true;
        if (queue.isEmpty()) {
            canDeleteFlag = false;
        }
        this.notifyAll();
        return value;
    }

    private synchronized void checkAdd() {
        while (!canAddFlag) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private synchronized void checkDelete() {
        while (!canDeleteFlag) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
