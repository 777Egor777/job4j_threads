package ru.job4j.synch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 26.12.2020
 */
@ThreadSafe
public final class SingleLockList<T> implements Iterable<T> {
    @GuardedBy("this")
    private final List<T> list = new LinkedList<>();

    public final synchronized void add(T value) {
        list.add(0, value);
    }

    public final synchronized T get(int index) {
        return list.get(index);
    }

    private synchronized List<T> copy(List<T> list) {
        return new LinkedList<>(list);
    }

    @Override
    public final synchronized Iterator<T> iterator() {
        return copy(list).iterator();
    }
}
