package ru.job4j.exam.store;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

/**
 * @author Egor Geraskin(yegeraskin13@gmail.com)
 * @version 1.0
 * @since 17.01.2021
 */
public final class Store {
    private final ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();

    private Store() {
    }

    private final static class Holder {
        private final static Store INSTANCE = new Store();
    }

    public static Store instOf() {
        return Holder.INSTANCE;
    }

    public void add(String json) {
        queue.offer(json);
    }

    public String getReport() {
        return queue.stream().collect(Collectors.joining("," + System.lineSeparator(), "[" + System.lineSeparator(),
                System.lineSeparator() + "]"));
    }
}
