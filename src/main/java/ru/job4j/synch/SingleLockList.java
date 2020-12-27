package ru.job4j.synch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.checkerframework.checker.nullness.qual.NonNull;
import ru.job4j.collection.ForwardLinked;

import java.util.Iterator;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 26.12.2020
 */
@ThreadSafe
public final class SingleLockList<T> implements Iterable<T> {
    @GuardedBy("this")
    private final ForwardLinked<T> list = new ForwardLinked<>();

    public final synchronized void add(T value) {
        list.add(value);
    }

    public final synchronized T get(int index) {
        return list.get(index);
    }

    private synchronized ForwardLinked<T> copy(ForwardLinked<T> list) {
        ForwardLinked<T> result = new ForwardLinked<>();
        list.getAll().forEach(result::add);
        return result;
    }

    @Override
    @NonNull
    public final synchronized Iterator<T> iterator() {
        return copy(list).iterator();
    }
}
