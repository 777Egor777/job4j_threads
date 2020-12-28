package ru.job4j;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CASCountTest {

    @Test
    public void whenThreeThreadsIncrement() {
        CASCount count = new CASCount();
        Thread threadA = new Thread(
                count::increment
        );
        Thread threadB = new Thread(
                count::increment
        );
        Thread threadC = new Thread(
                count::increment
        );
        threadA.start();
        threadB.start();
        threadC.start();
        try {
            threadA.join();
            threadB.join();
            threadC.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        assertThat(count.get(), is(3));
    }
}