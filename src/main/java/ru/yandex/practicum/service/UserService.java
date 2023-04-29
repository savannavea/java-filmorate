package ru.yandex.practicum.service;

import ru.yandex.practicum.model.User;

import java.util.List;

public interface UserService {

    User createUser(User user);

    User updateUser(User user);

    List<User> findAllUsers();

    User findUserById(int id);

    Integer addToFriends(int userId, int filmId);

    void deleteFromFriends(int userId, int filmId);

    List<User> getFriendsList(int id);

    List<User> getCommonFriends(int userId, int friendId);

}