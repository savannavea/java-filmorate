package ru.yandex.practicum.storage;

import ru.yandex.practicum.model.Film;
import ru.yandex.practicum.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserStorage {
    User createUser(User user);

    User updateUser(User user);

    void deleteAllUsers();

    Optional<User> findUserById(int id);

    List<User> findUserList();

    Set<Integer> findAllId();
}