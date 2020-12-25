package ru.job4j;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 25.12.2020
 */
public class Flag {
    private static volatile boolean flag = true;

    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> {
                    while (flag && !Thread.currentThread().isInterrupted()) {
                        System.out.println(Thread.currentThread().getName());
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );

        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            thread.interrupt();
        }
    }
}
