package ru.yandex.practicum.storage.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.model.User;
import ru.yandex.practicum.storage.UserStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> findUserById(int id) {

        String sql = "SELECT * FROM users WHERE user_id = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> mapRowToUser(rs), id);
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public User create(User user) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("user_id");
        int id = simpleJdbcInsert.executeAndReturnKey(toMap(user)).intValue();
        user.setId(id);

        return user;
    }


    @Override
    public User update(User user) {
        String sql = "UPDATE users SET email = ?, login = ?, name = ?, birthday = ? WHERE user_id = ?";
        jdbcTemplate.update(sql,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId());
        return user;
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, (rs, numOfRow) -> mapRowToUser(rs));
    }

    private User mapRowToUser(ResultSet rs) throws SQLException {
        int id = rs.getInt("user_id");
        String email = rs.getString("email");
        String login = rs.getString("login");
        String name = rs.getString("name");
        LocalDate birthday = rs.getDate("birthday").toLocalDate();
        return User.builder()
                .id(id)
                .email(email)
                .login(login)
                .name(name)
                .birthday(birthday)
                .build();
    }

    private Map<String, Object> toMap(User user) {
        Map<String, Object> values = new HashMap<>();
        values.put("email", user.getEmail());
        values.put("login", user.getLogin());
        values.put("name", user.getName());
        values.put("birthday", user.getBirthday());
        return values;
    }
}