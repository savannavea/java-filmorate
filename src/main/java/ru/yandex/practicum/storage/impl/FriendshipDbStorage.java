package ru.yandex.practicum.storage.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.storage.FriendshipStorage;

import java.util.List;

@Slf4j
@Component
public class FriendshipDbStorage implements FriendshipStorage {
    private final JdbcTemplate jdbcTemplate;

    public FriendshipDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Integer> findFriendsByUser(int id) {
        String sql = "SELECT friend_id FROM friendship WHERE user_id =? AND status = TRUE " +
                "UNION SELECT user_id FROM friendship WHERE friend_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("friend_id"), id, id);
    }

    @Override
    public boolean addFriend(int userId, int friendId) {
        String sql = "INSERT INTO friendship(user_id, friend_id, status) " +
                "VALUES (?, ?, FALSE)";
        return jdbcTemplate.update(sql, friendId, userId) > 0;
    }

    @Override
    public boolean updateFriend(int userId, int friendId, boolean status) {
        String sql = "UPDATE friendship SET status = ? " +
                "WHERE user_id = ? AND friend_id = ?";
        return jdbcTemplate.update(sql, status, userId, friendId) > 0;
    }

    @Override
    public boolean deleteFriend(int userId, int friendId) {
        String sql = "DELETE FROM friendship WHERE (user_id = ? AND friend_id = ?) OR (friend_id = ? AND user_id = ?)";
        return jdbcTemplate.update(sql, userId, friendId, userId, friendId) > 0;
    }
}
