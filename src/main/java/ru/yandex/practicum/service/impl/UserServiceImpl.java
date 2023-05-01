package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.model.User;
import ru.yandex.practicum.service.UserService;
import ru.yandex.practicum.storage.FilmStorage;
import ru.yandex.practicum.storage.UserStorage;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Override
    public User create(User user) {
        userStorage.create(user);
        return user;
    }

    @Override
    public User update(User user) {
        User userById = userStorage.findUserById(user.getId())
                .orElseThrow(() -> new NotFoundException("Not found user by id: " + user.getId()));

        return userStorage.update(userById);
    }

    @Override
    public List<User> getAll() {
        return userStorage.findAll();
    }

    @Override
    public User getUserById(int id) {
        User userById = userStorage.findUserById(id)
                .orElseThrow(() -> new NotFoundException("User's id %d doesn't found!" + id));

        return userById;
    }

    @Override
    public Integer addToFriends(int userId, int friendId) {
        User user = userStorage.findUserById(userId).orElseThrow(() -> new NotFoundException("User's id %d doesn't found!" + userId));
        User friend = userStorage.findUserById(friendId).orElseThrow(() -> new NotFoundException("User's id %d doesn't found!" + friendId));

        user.addFriend(friendId);
        friend.addFriend(userId);

        return userStorage.findUserById(userId).orElseThrow(() -> new NotFoundException("User's id %d doesn't found!" + userId)).getFriends().size();
    }

    @Override
    public void deleteFromFriends(int userId, int friendId) {
        User user = userStorage.findUserById(userId).orElseThrow(() -> new NotFoundException("User's id %d doesn't found!" + userId));
        User friend = userStorage.findUserById(friendId).orElseThrow(() -> new NotFoundException("User's id %d doesn't found!" + friendId));

        user.deleteFriend(friendId);
        friend.deleteFriend(userId);
    }

    @Override
    public List<Optional<User>> getFriendsList(int id) {
        return userStorage.findUserById(id).orElseThrow(() -> new NotFoundException("User's id %d doesn't found!" + id))
                .getFriends()
                .stream()
                .map(userStorage::findUserById)
                .collect(Collectors.toList());
    }

    @Override
    public List<Optional<User>> getCommonFriends(int userId, int friendId) {
        Set<Integer> users = userStorage.findUserById(userId).orElseThrow(() -> new NotFoundException("User's id %d doesn't found!" + userId)).getFriends();
        return userStorage.findUserById(friendId).orElseThrow(() -> new NotFoundException("User's id %d doesn't found!" + friendId)).getFriends().stream()
                .filter(users::contains)
                .map(userStorage::findUserById)
                .collect(Collectors.toList());
    }
}