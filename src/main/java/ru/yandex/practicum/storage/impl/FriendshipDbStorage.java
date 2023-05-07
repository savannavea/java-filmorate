package ru.yandex.practicum.storage.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.storage.FriendshipStorage;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class FriendshipDbStorage implements FriendshipStorage {
    private final JdbcTemplate jdbcTemplate;

    public FriendshipDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<List<Integer>> findFriendsByUser(int id) {
        String sql = "select friend_id from friendship where user_id =? and status = true " +
                "union select user_id from friendship where friend_id = ?";
        return Optional.ofNullable(jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("friend_id"), id, id));
    }

    @Override
    public boolean addFriend(int userId, int friendId) {
        String sql = "insert into friendship(user_id, friend_id, status) " +
                "values (?, ?, false)";
        return jdbcTemplate.update(sql, friendId, userId) > 0;
    }

    @Override
    public boolean updateFriend(int userId, int friendId, boolean status) {
        String sql = "update friendship set status = ? " +
                "where user_id = ? and friend_id = ?";
        return jdbcTemplate.update(sql, status, userId, friendId) > 0;
    }

    @Override
    public boolean deleteFriend(int userId, int friendId) {
        String sql = "delete from friendship where (user_id = ? AND friend_id = ?)";
        return jdbcTemplate.update(sql, userId, friendId) > 0;
    }
}
