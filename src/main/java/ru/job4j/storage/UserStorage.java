package ru.job4j.storage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.*;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 26.12.2020
 */
@ThreadSafe
public final class UserStorage implements Storage<User> {
    @GuardedBy("this")
    private final Map<Integer, User> users = new HashMap<>();

    @Override
    public final synchronized boolean add(User user) {
        boolean result = false;
        if (!users.containsKey(user.getId())) {
            result = true;
            users.put(user.getId(), User.of(user));
        }
        return result;
    }

    @Override
    public final synchronized boolean update(User user) {
        boolean result = false;
        if (users.containsKey(user.getId())) {
            result = true;
            users.put(user.getId(), User.of(user));
        }
        return result;
    }

    @Override
    public final synchronized boolean delete(User user) {
        boolean result = false;
        if (users.containsKey(user.getId())) {
            result = true;
            users.remove(user.getId());
        }
        return result;
    }

    public synchronized final boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        User fromUser = users.get(fromId);
        User toUser = users.get(toId);
        if (fromUser != null && toUser != null && fromUser.getAmount() >= amount) {
            result = true;
            update(fromUser.setAmount(fromUser.getAmount() - amount));
            update(toUser.setAmount(toUser.getAmount() + amount));
        }
        return result;
    }

    /**
     * Just for
     * unit tests.
     * @return list of all users
     */
    public final synchronized List<User> getAllUsers() {
        List<User> list =  new ArrayList<>(users.values());
        list.sort(User.CMP);
        return list;
    }
}
