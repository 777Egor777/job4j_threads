package ru.job4j;

import junit.framework.TestCase;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CountTest {
    private class ThreadCount extends Thread {
        private final Count count;

        private ThreadCount(Count count) {
            this.count = count;
        }

        @Override
        public void run() {
            count.increment();
        }
    }

    @Test
    public void whenExecute2ThreadsThen2() throws InterruptedException {
        final Count count = new Count();
        Thread first = new ThreadCount(count);
        Thread second = new ThreadCount(count);
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(count.get(), is(2));
    }
}