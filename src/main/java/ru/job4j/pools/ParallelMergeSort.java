package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 30.12.2020
 */
public final class ParallelMergeSort extends RecursiveTask<int[]> {
    private final int[] array;
    private final int leftBound;
    private final int rightBound;

    private ParallelMergeSort(final int[] array, final int leftBound, final int rightBound) {
        this.array = array;
        this.leftBound = leftBound;
        this.rightBound = rightBound;
    }

    private ParallelMergeSort(final int[] array) {
        this.array = array;
        this.leftBound = 0;
        this.rightBound = array.length - 1;
    }

    @Override
    protected int[] compute() {
        final int[] result;
        if (leftBound == rightBound) {
            result = new int[] {array[leftBound]};
        } else {
            final int mid = (leftBound + rightBound) / 2;
            ParallelMergeSort leftPart = new ParallelMergeSort(array, leftBound, mid);
            ParallelMergeSort rightPart = new ParallelMergeSort(array, mid + 1, rightBound);
            leftPart.fork();
            rightPart.fork();
            final int[] left = leftPart.join();
            final int[] right = rightPart.join();
            result = MergeSort.merge(left, right);
        }
        return result;
    }

    public static int[] sort(final int[] array) {
        ForkJoinPool pool = new ForkJoinPool();
        return pool.invoke(new ParallelMergeSort(array));
    }
}
