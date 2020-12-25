package ru.job4j.ref;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 25.12.2020
 */
public final class User {
    private final int id;
    private final String name;
    private final static int DEF_ID = 101;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public final static User of(int id, String name) {
        return new User(id, name);
    }

    public final static User of(String name) {
        return new User(DEF_ID, name);
    }

    public final static User of(User user) {
        return new User(user.id, user.name);
    }

    public final int getId() {
        return id;
    }

    public final String getName() {
        return name;
    }

    public final User setId(int id) {
        return User.of(id, name);
    }

    public final User setName(String name) {
        return User.of(id, name);
    }
}
