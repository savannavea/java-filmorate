package ru.yandex.practicum.storage;

import ru.yandex.practicum.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {
    User create(User user);

    User update(User user);

    void deleteAll();

    Optional<User> findUserById(int id);

    List<User> findAll();

}