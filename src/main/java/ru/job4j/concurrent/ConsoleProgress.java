package ru.job4j.concurrent;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 24.12.2020
 */
public class ConsoleProgress implements Runnable {
    private final char[] process = {'\\', '|', '/'};

    @Override
    public void run() {
        int counter = 0;
        while (!Thread.currentThread().isInterrupted()) {
            System.out.printf("\r load: %c", process[(counter++) % 3]);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        progress.interrupt();
    }
}
