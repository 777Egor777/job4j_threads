package ru.job4j.queue;

import java.util.function.Consumer;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 27.12.2020
 */
public final class QueueConsumer<T> extends Thread {
    private final SimpleBlockingQueue<T> queue;
    private boolean isRun = false;
    private T value;
    private boolean isInterrupted = false;

    public QueueConsumer(SimpleBlockingQueue<T> queue) {
        this.queue = queue;
    }

    @Override
    public final void run() {
        try {
            value = queue.poll();
        } catch (InterruptedException e) {
            isInterrupted = true;
        }
    }

    @Override
    public synchronized void start() {
        if (isRun) {
            try {
                throw new IllegalAccessException(String.format(
                        "Thread %s already started\n",
                        this.getName()
                ));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        isRun = true;
        super.start();
    }

    public T get() throws IllegalAccessException, InterruptedException {
        if (!(this.getState() == State.TERMINATED)) {
            throw new IllegalAccessException(String.format(
                    "Thread %s is running. Value hasn't calculated yet\n",
                    this.getName()));
        }
        if (isInterrupted) {
            throw new InterruptedException(String.format(
                    "Thread %s was interrupted due to finish of queue work.\n",
                    this.getName()));
        }
        return value;
    }
}
