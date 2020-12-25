package ru.job4j;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 25.12.2020
 */
public final class Cache {
    private static Cache cache;

    public synchronized static Cache instOf() {
        if (cache == null) {
            cache = new Cache();
        }
        return cache;
    }
}
