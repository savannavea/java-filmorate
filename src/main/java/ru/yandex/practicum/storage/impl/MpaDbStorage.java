package ru.yandex.practicum.storage.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.model.MPA;
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
    public Optional<MPA> findMPAById(int id) {
        String sql = "select * from rating_MPA where rating_id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, (rs, rowNum) -> makeMPA(rs), id));
    }

    @Override
    public List<MPA> findAll() {
        String sql = "select * from rating_MPA";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeMPA(rs));
    }

    private MPA makeMPA(ResultSet rs) throws SQLException {
        int mpaId = rs.getInt("rating_id");
        String mpaName = rs.getString("name_MPA");
        return MPA.builder().id(mpaId).name(mpaName).build();
    }
}
