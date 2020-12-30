package ru.job4j.cfuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 30.12.2020
 */
public class RunAsync {
    public static void iWork() throws InterruptedException {
        for (int i = 0; i < 10; ++i) {
            System.out.println("Вы: Я работаю");
            TimeUnit.SECONDS.sleep(1);
        }
    }

    public static CompletableFuture<Void> goToTrash() {
        return CompletableFuture.runAsync(
                () -> {
                    System.out.println("Сын: Мам/Пап, я пошёл выносить мусор");
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Сын: Мам/Пап, я вернулся!");
                }
        );
    }

    public static void runAsyncExample() throws InterruptedException {
        CompletableFuture<Void> gtt = goToTrash();
        iWork();
    }

    public static void thenRunExample() throws InterruptedException {
        CompletableFuture<Void> gtt = goToTrash();
        gtt.thenRun(
                () -> {
                    for (int i = 0; i < 3; ++i) {
                        System.out.println("Сын: я мою руки");
                        try {
                            TimeUnit.MILLISECONDS.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("Сын: я помыл руки");
                }
        );
        iWork();
    }

    public static void main(String[] args) {
        try {
            thenRunExample();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
