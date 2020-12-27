package ru.job4j.storage;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.Objects;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void testGetId() {
        int id = 1;
        int amount = 1000000;
        User user = new User(id, amount);
        assertThat(user.getId(), is(id));
    }

    @Test
    public void testGetAmount() {
        int id = 1;
        int amount = 1000000;
        User user = new User(id, amount);
        assertThat(user.getAmount(), is(amount));
    }

    @Test
    public void testSetId() {
        int id = 1;
        int amount = 1000000;
        User user = new User(id, amount);
        int newId = 10;
        user = user.setId(newId);
        assertThat(user.getId(), is(newId));
    }

    @Test
    public void testSetAmount() {
        int id = 1;
        int amount = 1000000;
        User user = new User(id, amount);
        int newAmount = 1000;
        user = user.setAmount(newAmount);
        assertThat(user.getAmount(), is(newAmount));
    }

    @Test
    public void testOf() {
        int id = 1;
        int amount = 1000000;
        User user = new User(id, amount);
        assertThat(user, is(User.of(user)));
    }

    @Test
    public void testTestToString() {
        int id = 1;
        int amount = 1000000;
        User user = new User(id, amount);
        String result = user.toString();
        String expected = "User{id=1, amount=1000000}";
        assertThat(result, is(expected));
    }

    @Test
    public void testTestHashCode() {
        int id = 1;
        int amount = 1000000;
        User user = new User(id, amount);
        assertThat(user.hashCode(), is(Objects.hash(id)));
    }

    @Test
    public void testTestEquals() {
        int id = 1;
        int amount = 1000000;
        User user1 = new User(id, amount);
        User user2 = new User(id, amount);
        assertThat(user1, is(user2));
    }
}