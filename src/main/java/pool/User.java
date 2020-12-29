package pool;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 29.12.2020
 */
public final class User {
    private final String name;
    private final String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public static User of(User user) {
        return new User(user.name, user.email);
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
