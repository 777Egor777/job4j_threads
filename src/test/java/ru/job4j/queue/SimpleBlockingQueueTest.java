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
    public QueueConsumer<Integer> consumer1;
    public QueueConsumer<Integer> consumer2;
    public QueueProducer<Integer> producer;
    public int value = 777;
    public int size = 10;
    Integer poisonPill = (int) 1e9 + 7;
    @Before
    public void doBeforeEachTest() {
        queue = new SimpleBlockingQueue<>(size);
        consumer1 = new QueueConsumer<>(queue, poisonPill);
        consumer2 = new QueueConsumer<>(queue, poisonPill);
        producer = new QueueProducer<>(queue, value);
    }

    @Test
    public void test() {
        consumer1.start();
        producer.start();
        try {
            producer.join();
        } catch (InterruptedException e) {
            producer.interrupt();
        }
        new QueueProducer<>(queue, poisonPill).start();
        try {
            consumer1.join();
        } catch (InterruptedException e) {
            consumer1.interrupt();
        }
        try {
            assertThat(consumer1.get(), is(value));
        } catch (IllegalAccessException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPoison() {
        consumer1.start();
        consumer2.start();
        producer.start();
        try {
            producer.join();
        } catch (InterruptedException e) {
            producer.interrupt();
        }
        new QueueProducer<>(queue, poisonPill).start();
        try {
            consumer1.join();
        } catch (InterruptedException e) {
            consumer1.interrupt();
        }
        try {
            consumer2.join();
        } catch (InterruptedException e) {
            consumer2.interrupt();
        }
        try {
            assertTrue(consumer1.get().equals(poisonPill)
                      || consumer2.get().equals(poisonPill));
        } catch (IllegalAccessException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);
        Thread producer = new Thread(
                () -> IntStream.range(0, 5).forEach(
                        value1 -> {
                            try {
                                queue.offer(value1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                )
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