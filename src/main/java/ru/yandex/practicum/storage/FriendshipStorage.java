package ru.yandex.practicum.storage;

import java.util.List;

public interface FriendshipStorage {

    List<Integer> findFriendsByUser(int id);

    boolean addFriend(int userId, int friendId);

    boolean updateFriend(int userId, int friendId, boolean status);

    boolean deleteFriend(int userId, int friendId);
}