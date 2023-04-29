package ru.yandex.practicum.storage;

import ru.yandex.practicum.model.User;

import java.util.List;
import java.util.Set;

public interface UserStorage {
    User createUser(User user);

    User updateUser(User user);

    void deleteAllUsers();

    User findUserById(int id);

    List<User> getUserList();

    Set<Integer> getAllId();
}