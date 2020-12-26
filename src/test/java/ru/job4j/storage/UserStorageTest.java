package ru.job4j.storage;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class UserStorageTest {
    public UserStorage storage;

    @Before
    public void doBeforeEachTest() {
        storage = new UserStorage();
    }

    @Test
    public void add() {
        User user = new User(10, 7000);
        storage.add(user);
        assertThat(storage.getAllUsers().get(0), is(user));
        assertThat(storage.getAllUsers().size(), is(1));
    }

    @Test
    public void update() {
        User user = new User(10, 7000);
        storage.add(user);
        User newUser = user.setAmount(700);
        storage.update(user, newUser);
        assertThat(storage.getAllUsers().get(0), is(newUser));
    }

    @Test
    public void delete() {
        User user = new User(10, 7000);
        storage.add(user);
        storage.delete(User.of(user));
        assertTrue(storage.getAllUsers().isEmpty());
    }

    @Test
    public void transfer() {
        User egor = new User(1, 100000);
        User ivan = new User(2, 500000);
        storage.add(egor);
        storage.add(ivan);
        assertTrue(storage.transfer(1, 2, 50000));
        assertThat(storage.getAllUsers().get(0).getAmount(), is(50000));
        assertThat(storage.getAllUsers().get(1).getAmount(), is(550000));
    }

    @Test
    public void whenCouldNotTransfer() {
        User egor = new User(1, 49000);
        User ivan = new User(2, 500000);
        storage.add(egor);
        storage.add(ivan);
        assertFalse(storage.transfer(1, 2, 50000));
    }
}