package ru.job4j.queue;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 27.12.2020
 */
public class QueueProducer<T> extends Thread {
    private final SimpleBlockingQueue<T> queue;
    private boolean isRun = false;
    private final T value;
    private InterruptedException ex = null;
    public QueueProducer(SimpleBlockingQueue<T> queue, T value) {
        this.queue = queue;
        this.value = value;
    }

    @Override
    public final void run() {
        try {
            queue.offer(value);
        } catch (InterruptedException e) {
            ex = e;
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
        try {
            this.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (ex != null) {
            ex.printStackTrace();
        }
    }
}
