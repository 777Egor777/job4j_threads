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
    @GuardedBy("this")
    private boolean isFinished = false;
    private final int size;

    public SimpleBlockingQueue(int size) {
        this.size = size;
    }

    public final synchronized void offer(T value) throws InterruptedException {
        checkAdd();
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedException(String.format(
                    "Thread %s was interrupted\n",
                    Thread.currentThread().getName()));
        }
        queue.offer(value);
        canDeleteFlag = true;
        if (queue.size() >= size) {
            canAddFlag = false;
        }
        this.notifyAll();
    }

    public final synchronized T poll() throws InterruptedException {
        checkDelete();
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedException(String.format(
                    "Thread %s was interrupted\n",
                    Thread.currentThread().getName()));
        }
        T value = queue.poll();
        canAddFlag = true;
        if (queue.isEmpty()) {
            canDeleteFlag = false;
        }
        this.notifyAll();
        return value;
    }

    private synchronized void checkAdd() {
        while (!Thread.currentThread().isInterrupted() && !canAddFlag && !isFinished) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if (isFinished) {
            Thread.currentThread().interrupt();
        }
    }

    private synchronized void checkDelete() {
        while (!Thread.currentThread().isInterrupted() && !canDeleteFlag && !isFinished) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if (isFinished) {
            Thread.currentThread().interrupt();
        }
    }

    public synchronized final void finish() {
        isFinished = true;
        this.notifyAll();
    }
}
