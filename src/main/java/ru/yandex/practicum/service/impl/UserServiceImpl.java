package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.model.User;
import ru.yandex.practicum.service.UserService;
import ru.yandex.practicum.storage.FriendshipStorage;
import ru.yandex.practicum.storage.UserStorage;

import java.util.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserStorage userStorage;
    private final FriendshipStorage friendshipStorage;

    @Override
    public User create(User user) {
        userStorage.create(user);
        return user;
    }

    @Override
    public User update(User user) {
        userStorage
                .findUserById(user.getId())
                .orElseThrow(() -> new NotFoundException("Not found user by id: " + user.getId()));

        return userStorage.update(user);
    }

    @Override
    public List<User> getAll() {
        return userStorage.findAll();
    }

    @Override
    public User getUserById(int id) {
        return userStorage.findUserById(id)
                .orElseThrow(() -> new NotFoundException("User's id %d doesn't found!" + id));
    }

    @Override
    public List<Integer> addToFriends(int userId, int friendId) {
        getUserById(userId);
        getUserById(friendId);
        boolean isUserToFriend = friendshipStorage.findFriendsByUser(userId).contains(friendId);
        boolean isFriendToUser = friendshipStorage.findFriendsByUser(friendId).contains(userId);
        if (!isUserToFriend && !isFriendToUser) {
            friendshipStorage.addFriend(userId, friendId);
        } else if (isUserToFriend && !isFriendToUser) {
            friendshipStorage.updateFriend(friendId, userId, true);
        } else {
            log.debug("Repeated friend request from user with id {} to user with id {}", userId, friendId);
        }
        return friendshipStorage.findFriendsByUser(userId);
    }

    @Override
    public void deleteFromFriends(int userId, int friendId) {
        friendshipStorage.deleteFriend(userId, friendId);
    }

    @Override
    public Optional<List<User>> getFriendsList(int userId) {
        List<User> friendsList = new ArrayList<>();

        for (Integer friend : friendshipStorage.findFriendsByUser(userId)) {
            friendsList.add(userStorage.findUserById(friend).get());
        }

        log.info("List friends User №" + userId);
        return Optional.ofNullable(friendsList);

    }

    @Override
    public List<User> getCommonFriends(int user1Id, int user2Id) {
        Set<Integer> common = new HashSet<>(friendshipStorage.findFriendsByUser(user1Id));
        common.retainAll(friendshipStorage.findFriendsByUser(user2Id));

        List<User> commonFriends = new ArrayList<>();

        for (Integer cm : common) {
            commonFriends.add(userStorage.findUserById(cm).get());
        }

        log.info("List of mutual friends User №" + user1Id + " and User №" + user2Id + "ready");
        return commonFriends;

    }
}