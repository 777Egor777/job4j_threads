package ru.job4j.nonblock;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ConcurrentCacheTest {

    @Test
    public void whenException() {
        AtomicReference<Exception> ex = new AtomicReference<>(new Exception());
        Cache<Base> cache = new ConcurrentCache();
        Base base = new Base(1, "Egor");
        cache.add(base);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread threadA = new Thread(
                () -> {
                    Base baseA = base.setName("NotEgor");
                    try {
                        cache.update(baseA);
                    } catch (Exception e) {
                        ex.set(e);
                    }
                }
        );
        Thread threadB = new Thread(
                () -> {
                    Base baseB = base.setName("Egor2");
                    try {
                        cache.update(baseB);
                    } catch (Exception e) {
                        ex.set(e);
                    }
                }
        );
        threadA.start();
        threadB.start();
        try {
            threadA.join();
            threadB.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertThat(ex.get().getMessage(), is("Concurrent version modification"));
    }
}