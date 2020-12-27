package ru.job4j.queue;

import org.junit.Before;
import org.junit.Test;

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
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}