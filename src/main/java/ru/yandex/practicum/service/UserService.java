package ru.yandex.practicum.service;

import ru.yandex.practicum.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User create(User user);

    User update(User user);

    List<User> getAll();

    User getUserById(int id);

    List<Integer> addToFriends(int userId, int filmId);

    void deleteFromFriends(int userId, int filmId);

    Optional<List<User>> getFriendsList(int id);

    List<User> getCommonFriends(int userId, int friendId);
}