package ru.job4j.queue;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {
    public SimpleBlockingQueue<Integer> queue;
    public QueueConsumer<Integer> consumer;
    public QueueProducer<Integer> producer;
    public int value = 777;
    public int size = 10;
    @Before
    public void doBeforeEachTest() {
        queue = new SimpleBlockingQueue<>(size);
        consumer = new QueueConsumer<>(queue);
        producer = new QueueProducer<>(queue, value);
    }

    @Test
    public void test() {
        consumer.start();
        producer.start();
        try {
            consumer.join();
        } catch (InterruptedException e) {
            consumer.interrupt();
        }
        try {
            producer.join();
        } catch (InterruptedException e) {
            producer.interrupt();
        }
        try {
            assertThat(consumer.get(), is(value));
        } catch (IllegalAccessException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);
        Thread producer = new Thread(
                () -> {
                    IntStream.range(0, 5).forEach(
                            value1 -> {
                                try {
                                    queue.offer(value1);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                    );
                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer, is(Arrays.asList(0, 1, 2, 3, 4)));
    }
}