package ru.job4j.cfuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 30.12.2020
 */
public class SupplyAsync {
    public static CompletableFuture<String> buyProduct(String product) {
        return CompletableFuture.supplyAsync(
                () -> {
                    System.out.println("Сын: Мам/Пап, я пошёл в магазин");
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Сын: Мам/Пап, я купил " + product);
                    return product;
                }
        );
    }

    public static void supplyAsyncExample() throws InterruptedException, ExecutionException {
        CompletableFuture<String> bm = buyProduct("Молоко");
        RunAsync.iWork();
        System.out.println("Куплено: " + bm.get());
    }

    public static void thenAcceptExample() throws InterruptedException, ExecutionException {
        CompletableFuture<String> bm = buyProduct("Молоко");
        bm.thenAccept((product) -> System.out.println("Сын: Я убрал " + product + " в холодильник"));
        RunAsync.iWork();
        System.out.println("Куплено: " + bm.get());
    }

    public static void thenApplyExample() throws InterruptedException, ExecutionException {
        CompletableFuture<String> bm = buyProduct("Молоко").
                thenApply((product) -> "Сын: Я налил тебе в кружку " + product + ". Держи.");
        RunAsync.iWork();
        System.out.println(bm.get());
    }

    public static void main(String[] args) {
        try {
            thenApplyExample();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
