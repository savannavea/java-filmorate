package ru.yandex.practicum.storage.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.model.Genre;
import ru.yandex.practicum.storage.GenreStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Genre> findGenreById(int id) {
        String sql = "SELECT * FROM genre WHERE genre_id = ?";
        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, (rs, rowNum) -> makeGenre(rs), id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Genre> findAll() {
        String sql = "SELECT * FROM genre";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeGenre(rs));
    }

    public List<Genre> findGenresByFilmId(int filmId) {
        String sql = "SELECT GENRE_ID FROM film_genre WHERE film_id = ?;";
        List<Integer> genreIds = jdbcTemplate.queryForList(sql, Integer.class, filmId);
        return genreIds.stream()
                .sorted()
                .map(this::findGenreById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .distinct()
                .collect(Collectors.toList());
    }

    private Genre makeGenre(ResultSet rs) throws SQLException {
        return Genre.builder()
                .id(rs.getInt("genre_id"))
                .name(rs.getString("name"))
                .build();
    }
}