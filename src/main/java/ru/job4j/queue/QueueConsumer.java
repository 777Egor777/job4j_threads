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

    public QueueConsumer(SimpleBlockingQueue<T> queue) {
        this.queue = queue;
    }

    @Override
    public final void run() {
        value = queue.poll();
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

    public T get() throws IllegalAccessException {
        if (!(this.getState() == State.TERMINATED)) {
            throw new IllegalAccessException(String.format(
                    "Thread %s is running. Value hasn't calculated yet\n",
                    this.getName()));
        }
        return value;
    }
}
