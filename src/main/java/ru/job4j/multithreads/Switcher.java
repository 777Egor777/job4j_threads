package ru.job4j.multithreads;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 31.12.2020
 */
public final class Switcher {
    private final static MasterSlaveBarrier BARRIER = new MasterSlaveBarrier();
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        BARRIER.tryMaster();
                        System.out.println("Thread A");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        BARRIER.doneMaster();
                    }
                }
        );
        Thread second = new Thread(
                () -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        BARRIER.trySlave();
                        System.out.println("Thread B");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        BARRIER.doneSlave();
                    }
                }
        );
        first.start();
        second.start();
        try {
            first.join();
        } catch (InterruptedException e) {
            first.interrupt();
        }
        try {
            second.join();
        } catch (InterruptedException e) {
            second.interrupt();
        }
    }
}
