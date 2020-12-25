package ru.job4j;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 25.12.2020
 */
public final class DCLSingleton {
    private static volatile DCLSingleton instance;

    private DCLSingleton() {
    }

    public static DCLSingleton getInstance() {
        if (instance == null) {
            synchronized (DCLSingleton.class) {
                if (instance == null) {
                    instance = new DCLSingleton();
                }
            }
        }
        return instance;
    }
}
