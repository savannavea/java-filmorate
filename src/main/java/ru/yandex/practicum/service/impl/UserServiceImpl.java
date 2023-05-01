package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.model.User;
import ru.yandex.practicum.service.UserService;
import ru.yandex.practicum.storage.FilmStorage;
import ru.yandex.practicum.storage.UserStorage;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Override
    public User create(User user) {
        userStorage.createUser(user);
        return user;
    }

    @Override
    public User update(User user) {
        if (!userStorage.findAllId().contains(user.getId())) {
            throw new NotFoundException("User  does not exist");
        }
        userStorage.updateUser(user);
        return user;
    }

    @Override
    public List<User> getAll() {
        return userStorage.findUserList();
    }

    @Override
    public User getUserById(int id) {
        if (!userStorage.findAllId().contains(id)) {
            throw new NotFoundException(String.format("User's id %d doesn't found!", id));
        }
        return userStorage.findUserById(id);
    }

    @Override
    public Integer addToFriends(int userId, int friendId) {
        User user = userStorage.findUserById(userId);
        User friend = userStorage.findUserById(friendId);
        user.addFriend(friendId);
        friend.addFriend(userId);

        return userStorage.findUserById(userId).getFriends().size();
    }

    @Override
    public void deleteFromFriends(int userId, int friendId) {
        User user = userStorage.findUserById(userId);
        User friend = userStorage.findUserById(friendId);
        user.deleteFriend(friendId);
        friend.deleteFriend(userId);
    }

    @Override
    public List<User> getFriendsList(int id) {
        return userStorage.findUserById(id).getFriends().stream()
                .map(userStorage::findUserById)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getCommonFriends(int userId, int friendId) {
        Set<Integer> users = userStorage.findUserById(userId).getFriends();
        return userStorage.findUserById(friendId).getFriends().stream()
                .filter(users::contains)
                .map(userStorage::findUserById)
                .collect(Collectors.toList());
    }
}