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
public final class SimpleBlockingQueue<T> implements Refresh {
    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final int size;
    @GuardedBy("this")
    private boolean isFinished = false;
    private final static int REFRESH_PERIOD_SEC = 5;
    private final AutoRefresh auto = new AutoRefresh(REFRESH_PERIOD_SEC, this);
    @GuardedBy("this")
    private long lastUpdateMoment = System.currentTimeMillis();
    private final static long MAX_ALL_WAIT_PERIOD_MS = 10L * 1000L;

    public SimpleBlockingQueue(int size) {
        this.size = size;
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() >= size) {
            wait();
            if (isFinished) {
                throw new InterruptedException();
            }
        }
        queue.offer(value);
        lastUpdateMoment = System.currentTimeMillis();
        notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
        T value;
        while (queue.isEmpty()) {
            wait();
            if (isFinished) {
                throw new InterruptedException();
            }
        }
        value = queue.poll();
        lastUpdateMoment = System.currentTimeMillis();
        notifyAll();
        return value;
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public synchronized void refresh() {
        long cur = System.currentTimeMillis();
        long diff = cur - lastUpdateMoment;
        if (diff > MAX_ALL_WAIT_PERIOD_MS) {
            auto.shutDown();
            isFinished = true;
            notifyAll();
        }
    }
}
