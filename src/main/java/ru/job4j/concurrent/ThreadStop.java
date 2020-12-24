package ru.job4j.concurrent;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 24.12.2020
 */
public class ThreadStop {
    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> {
                    int count = 0;
                    while (!Thread.currentThread().isInterrupted()) {
                        System.out.println(count++);
                    }
                }
        );
        thread.start();
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();

    }
}
