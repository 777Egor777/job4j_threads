package ru.job4j.ref;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 25.12.2020
 */
public class UserCache {
    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();

    public final void add(User user) {
        users.put(id.incrementAndGet(), User.of(user));
    }

    public final User findById(int id) {
        if (!users.containsKey(id)) {
            throw new NoSuchElementException("No user with such id");
        }
        return User.of(users.get(id));
    }

    public final List<User> findAll() {
        return users.values().stream().map(User::of).collect(Collectors.toList());
    }
}
