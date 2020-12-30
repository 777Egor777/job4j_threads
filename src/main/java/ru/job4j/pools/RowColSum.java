package ru.job4j.pools;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 30.12.2020
 */
public final class RowColSum {
    public static final class Sums {
        private final int rowSum;
        private final int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        public int getRowSum() {
            return rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Sums sums = (Sums) o;
            return rowSum == sums.rowSum && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }
    }

    private static int rowSum(int[][] matrix, int row) {
        int result = 0;
        for (int i = 0; i < matrix.length; ++i) {
            result += matrix[row][i];
        }
        return result;
    }

    private static int colSum(int[][] matrix, int col) {
        int result = 0;
        for (int[] ints : matrix) {
            result += ints[col];
        }
        return result;
    }

    public static Sums[] sum(int[][] matrix) {
        int n = matrix.length;
        Sums[] result = new Sums[n];
        for (int i = 0; i < n; i++) {
            result[i] = new Sums(rowSum(matrix, i),
                                 colSum(matrix, i));
        }
        return result;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int n = matrix.length;
        Sums[] result = new Sums[n];
        List<CompletableFuture<Sums>> list = new LinkedList<>();
        for (int i = 0; i < n; ++i) {
            list.add(getSum(matrix, i));
        }
        for (int i = 0; i < n; ++i) {
            result[i] = list.get(i).get();
        }
        return result;
    }

    public static CompletableFuture<Sums> getSum(int[][] matrix, int id) {
        return CompletableFuture.supplyAsync(
                () -> {
                    int rowSum = 0;
                    int colSum = 0;
                    for (int i = 0; i < matrix.length; ++i) {
                        rowSum += matrix[id][i];
                        colSum += matrix[i][id];
                    }
                    return new Sums(rowSum, colSum);
                }
        );
    }
}
