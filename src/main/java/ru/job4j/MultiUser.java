package ru.job4j;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 27.12.2020
 */
public class MultiUser {
    public static void main(String[] args) {
        Barrier barrier = new Barrier();
        Thread master = new Thread(
                () -> {
                    barrier.check();
                    System.out.printf("%s start\n", Thread.currentThread().getName());
                },
                "Master"
        );
        Thread slave = new Thread(
                () -> {
                    System.out.printf("%s start\n", Thread.currentThread().getName());
                    barrier.on();
                },
                "Slave"
        );
        master.start();
        slave.start();
    }
}
