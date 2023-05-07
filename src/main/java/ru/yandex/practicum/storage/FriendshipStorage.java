package ru.yandex.practicum.storage;

import java.util.List;
import java.util.Optional;

public interface FriendshipStorage {

    Optional<List<Integer>> findFriendsByUser(int id);

    boolean addFriend(int userId, int friendId);

    boolean updateFriend(int userId, int friendId, boolean status);

    boolean deleteFriend(int userId, int friendId);
}