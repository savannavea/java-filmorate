package ru.yandex.practicum.service;

import ru.yandex.practicum.model.User;

import java.util.List;

public interface UserService {

    User create(User user);

    User update(User user);

    List<User> findAll();
}