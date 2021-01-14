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
    private final int size;

    public SimpleBlockingQueue(int size) {
        this.size = size;
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() >= size) {
            wait();
        }
        queue.offer(value);
        notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
        T value;
        while (queue.isEmpty()) {
            wait();
        }
        value = queue.poll();
        notifyAll();
        return value;
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }


}
