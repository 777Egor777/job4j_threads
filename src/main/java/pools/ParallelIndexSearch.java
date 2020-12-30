package pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 30.12.2020
 */
public final class ParallelIndexSearch<T> extends RecursiveTask<Integer> {
    private T[] array;
    private int leftBound;
    private int rightBound;
    private T obj;

    private ParallelIndexSearch(T[] array, int leftBound, int rightBound, T obj) {
        this.array = array;
        this.leftBound = leftBound;
        this.rightBound = rightBound;
        this.obj = obj;
    }

    private ParallelIndexSearch(T[] array, T obj) {
        this.array = array;
        this.leftBound = 0;
        this.rightBound = array.length - 1;
        this.obj = obj;
    }

    public ParallelIndexSearch() {
    }

    public Integer findIndex(T[] array, T obj) {
        return new ForkJoinPool().invoke(new ParallelIndexSearch<>(array, obj));
    }

    @Override
    protected Integer compute() {
        final int result;
        if (leftBound == rightBound) {
            if (array[leftBound].equals(obj)) {
                result = leftBound;
            } else {
                result = -1;
            }
        } else {
            final int mid = (leftBound + rightBound) / 2;
            final ParallelIndexSearch<T> leftPart = new ParallelIndexSearch<>(array, leftBound, mid, obj);
            final ParallelIndexSearch<T> rightPart = new ParallelIndexSearch<>(array, mid + 1, rightBound, obj);
            leftPart.fork();
            rightPart.fork();
            final int leftResult = leftPart.join();
            final int rightResult = rightPart.join();
            if (leftResult != -1 && rightResult != -1) {
                result = Math.min(leftResult, rightResult);
            } else {
                result = Math.max(leftResult, rightResult);
            }
        }
        return result;
    }
}
