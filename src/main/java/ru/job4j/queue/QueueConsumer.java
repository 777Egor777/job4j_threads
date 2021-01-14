package ru.job4j.queue;

import java.util.Objects;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 27.12.2020
 */
public final class QueueConsumer<T> extends Thread {
    private final SimpleBlockingQueue<T> queue;
    private boolean isRun = false;
    private T value;
    private final T poisonPill;

    public QueueConsumer(SimpleBlockingQueue<T> queue, T poisonPill) {
        this.queue = queue;
        this.poisonPill = poisonPill;
    }

    @Override
    public void run() {
        try {
            value = queue.poll();
            if (Objects.equals(value, poisonPill)) {
                queue.offer(poisonPill);
            }
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

    public T get() throws IllegalAccessException, InterruptedException {
        if (this.isInterrupted()) {
            throw new InterruptedException(String.format(
                    "Thread %s was interrupted due to finish of queue work.\n",
                    this.getName()));
        }
        if (!isTerminated()) {
            throw new IllegalAccessException(String.format(
                    "Thread %s is running. Value hasn't calculated yet\n",
                    this.getName()));
        }
        return value;
    }
}
