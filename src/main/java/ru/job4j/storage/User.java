package ru.job4j.storage;

import net.jcip.annotations.Immutable;

import java.util.Comparator;
import java.util.Objects;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 26.12.2020
 */
@Immutable
public final class User {
    public final static Comparator<User> CMP = Comparator.comparingInt(u -> u.id);

    private final int id;
    private final int amount;

    public User(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public User setId(int id) {
        return new User(id, amount);
    }

    public User setAmount(int amount) {
        return new User(id, amount);
    }

    public static User of(User user) {
        return new User(user.id, user.amount);
    }

    @Override
    public String toString() {
        return String.format("User{id=%d, amount=%d}", id, amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        User user = (User) obj;
        return user.id == id;
    }
}
