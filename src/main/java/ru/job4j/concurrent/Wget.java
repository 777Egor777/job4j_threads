package ru.job4j.concurrent;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 24.12.2020
 */
public class Wget {
    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> {
                    try {
                        for (int i = 0; i <= 100; ++i) {
                            System.out.printf("\rLoading: %d%%", i);
                            Thread.sleep(1000);
                        }
                        Thread.sleep(5000);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
        );
        thread.start();

        while (!(thread.getState() == Thread.State.TERMINATED)) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("\nProgress completed!");
    }
}
