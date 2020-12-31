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
    @GuardedBy("this")
    private boolean canAddFlag = true;
    @GuardedBy("this")
    private boolean canDeleteFlag = false;
    private final int size;

    public SimpleBlockingQueue(int size) {
        this.size = size;
    }

    public final synchronized void offer(T value) throws InterruptedException {
        checkAdd();
        if (canAddFlag) {
            queue.offer(value);
        } else
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedException(String.format(
                    "Thread %s was interrupted\n",
                    Thread.currentThread().getName()));
        }
        canDeleteFlag = true;
        if (queue.size() >= size) {
            canAddFlag = false;
        }
        this.notifyAll();
    }

    public final synchronized T poll() throws InterruptedException {
        checkDelete();
        T value = null;
        if (canDeleteFlag) {
            value = queue.poll();
        } else
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedException(String.format(
                    "Thread %s was interrupted\n",
                    Thread.currentThread().getName()));
        }
        canAddFlag = true;
        if (queue.isEmpty()) {
            canDeleteFlag = false;
        }
        this.notifyAll();
        return value;
    }

    private synchronized void checkAdd() {
        while (!Thread.currentThread().isInterrupted() && !canAddFlag) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private synchronized void checkDelete() {
        while (!Thread.currentThread().isInterrupted() && !canDeleteFlag) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public final synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}
