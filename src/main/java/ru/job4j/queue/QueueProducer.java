package ru.job4j.queue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 27.12.2020
 */
public final class QueueProducer<T> extends Thread {
    private final SimpleBlockingQueue<T> queue;
    private boolean isRun = false;
    private final T value;

    public QueueProducer(SimpleBlockingQueue<T> queue, T value) {
        this.queue = queue;
        this.value = value;
    }

    @Override
    public void run() {
        try {
            queue.offer(value);
        } catch (InterruptedException e) {
            this.interrupt();
        }
    }


    @Override
    public void start() {
        if (!isRun) {
            isRun = true;
            super.start();
        }
    }

    public boolean isTerminated() {
        return this.getState() == State.TERMINATED;
    }
}
