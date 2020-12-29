package pool;

import ru.job4j.queue.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 29.12.2020
 */
public final class ThreadPool {
    private static final int SIZE = Runtime.getRuntime().availableProcessors();
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> queue = new SimpleBlockingQueue<>(SIZE);

    public ThreadPool() {
        init();
    }

    private void init() {
        for (int index = 0; index < SIZE; ++index) {
            threads.add(new BaseThread(queue));
        }
        for (Thread thread : threads) {
            thread.start();
        }
    }

    public void work(Runnable job) {
        try {
            queue.offer(job);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (Thread thread : threads) {
            if (thread.getState() == Thread.State.WAITING) {
                thread.notify();
            }
        }
    }

    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }
}
