package ru.job4j.nonblock;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 28.12.2020
 */
public final class OptimisticException extends RuntimeException {
    public OptimisticException() {
        super();
    }

    public OptimisticException(String message) {
        super(message);
    }
}
