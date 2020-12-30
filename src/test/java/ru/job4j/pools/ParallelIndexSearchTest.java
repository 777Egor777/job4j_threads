package ru.job4j.pools;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ParallelIndexSearchTest {

    @Test
    public void findIndex() {
        Integer[] array = new Integer[] {0, 1, 2, 3, 4, 5, 6};
        int result = new ParallelIndexSearch<Integer>().findIndex(array, 4);
        assertThat(result, is(4));
    }
}