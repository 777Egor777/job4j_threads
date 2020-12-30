package pools;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 30.12.2020
 */
public final class MergeSort {
    public static int[] sort(int[] array) {
        return sort(array, 0, array.length - 1);
    }

    public static int[] sort(final int[] array, final int from, final int to) {
        final int[] result;
        if (from == to) {
            result = new int[] {array[from]};
        } else {
            final int mid = (from + to) / 2;
            result = merge(sort(array, from, mid), sort(array, mid + 1, to));
        }
        return result;
    }

    public static int[] merge(final int[] firstAr, final int[] secondAr) {
        final int[] result = new int[firstAr.length + secondAr.length];
        int pos = 0;
        int firstPos = 0;
        int secondPos = 0;
        while (pos < result.length) {
            int nextEl;
            if (firstPos == firstAr.length) {
                nextEl = secondAr[secondPos++];
            } else if (secondPos == secondAr.length) {
                nextEl = firstAr[firstPos++];
            } else if (firstAr[firstPos] <= secondAr[secondPos]) {
                nextEl = firstAr[firstPos++];
            } else {
                nextEl = secondAr[secondPos++];
            }
            result[pos++] = nextEl;
        }
        return result;
    }
}
