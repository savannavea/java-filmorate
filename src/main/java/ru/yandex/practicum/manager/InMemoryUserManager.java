package ru.yandex.practicum.manager;

import ru.yandex.practicum.model.User;

import java.util.Collection;

public interface InMemoryUserManager {

    User create(User user);

    User update(User user);

    Collection<User> findAll();
}
