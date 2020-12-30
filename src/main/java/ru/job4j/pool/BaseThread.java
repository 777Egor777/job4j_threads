package ru.job4j.pool;

import ru.job4j.queue.SimpleBlockingQueue;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 29.12.2020
 */
public class BaseThread extends Thread {
    private final SimpleBlockingQueue<Runnable> queue;

    public BaseThread(SimpleBlockingQueue<Runnable> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            try {
                synchronized (this) {
                    queue.poll().run();
                }
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }
    }
}
