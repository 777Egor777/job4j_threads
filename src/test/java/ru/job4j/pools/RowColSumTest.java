package ru.job4j.pools;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static ru.job4j.pools.RowColSum.*;

public class RowColSumTest {
    public final static int SIZE = 3;
    public int[][] matrix;
    public Sums[] result;

    @Before
    public void setUp() {
        matrix = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9} };
        result = new Sums[]{new Sums(6, 12),
                             new Sums(15, 15),
                             new Sums(24, 18) };
    }

    @Test
    public void sum() {
        assertThat(RowColSum.sum(matrix), is(result));
    }

    @Test
    public void asyncSum() {
        try {
            assertThat(RowColSum.asyncSum(matrix), is(result));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}