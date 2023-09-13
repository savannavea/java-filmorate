package ru.yandex.practicum.storage.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.model.Mpa;
import ru.yandex.practicum.storage.MpaStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class MpaDbStorage implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;

    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Mpa> findMpaById(int id) {
        String sql = "SELECT * FROM mpa WHERE mpa_id = ?";
        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, (rs, rowNum) -> makeMPA(rs), id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Mpa> findAll() {
        String sql = "SELECT * FROM mpa";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeMPA(rs));
    }

    private Mpa makeMPA(ResultSet rs) throws SQLException {
        int mpaId = rs.getInt("mpa_id");
        String mpaName = rs.getString("name");
        return Mpa.builder().id(mpaId).name(mpaName).build();
    }
}