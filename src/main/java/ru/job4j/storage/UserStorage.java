package ru.job4j.storage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 26.12.2020
 */
@ThreadSafe
public final class UserStorage implements Storage<User> {
    @GuardedBy("this")
    private final List<User> users = new ArrayList<>();

    @Override
    public final synchronized boolean add(User user) {
        boolean result = false;
        if (!users.contains(user)) {
            result = true;
            users.add(User.of(user));
        }
        return result;
    }

    @Override
    public final synchronized boolean update(User user1, User user2) {
        boolean result = false;
        if (users.contains(user1)) {
            result = true;
            users.set(users.indexOf(user1), User.of(user2));
        }
        return result;
    }

    @Override
    public final synchronized boolean delete(User user) {
        boolean result = false;
        if (users.contains(user)) {
            result = true;
            users.remove(user);
        }
        return result;
    }

    private synchronized int findIndexById(int id) {
        int result = -1;
        for (int i = 0; i < users.size(); ++i) {
            if (users.get(i).getId() == id) {
                result = i;
                break;
            }
        }
        return result;
    }

    public synchronized final boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        int fromIndex = findIndexById(fromId);
        int toIndex = findIndexById(toId);
        System.out.println(fromIndex);
        System.out.println(toIndex);
        if (fromIndex != -1 && toIndex != -1 && users.get(fromIndex).getAmount() >= amount) {
            result = true;
            User fromUser = users.get(fromIndex);
            User toUser = users.get(toIndex);
            users.set(fromIndex, new User(fromUser.getId(), fromUser.getAmount() - amount));
            users.set(toIndex, new User(toUser.getId(), toUser.getAmount() + amount));
        }
        return result;
    }

    /**
     * Just for
     * unit tests.
     * @return list of all users
     */
    public final synchronized List<User> getAllUsers() {
        return users;
    }
}
