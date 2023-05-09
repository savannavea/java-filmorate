package ru.yandex.practicum.storage.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.model.Film;
import ru.yandex.practicum.model.Mpa;
import ru.yandex.practicum.storage.FilmStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final GenreDbStorage genreDbStorage;


    public FilmDbStorage(JdbcTemplate jdbcTemplate, GenreDbStorage genreDbStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.genreDbStorage = genreDbStorage;
    }

    @Override
    public Film create(Film film) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("films")
                .usingGeneratedKeyColumns("id");
        int id = simpleJdbcInsert.executeAndReturnKey(toMap(film)).intValue();
        film.setId(id);
        film.getGenres().forEach(genre -> addGenreToFilm(id, genre.getId()));
        return film;
    }

    @Override
    public Film update(Film film) {
        String sql = "UPDATE films SET name = ?, description = ?, release_date = ?, " +
                "duration = ?, mpa_id = ? WHERE id = ?";

        jdbcTemplate.update(sql,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());

        clearGenresFromFilm(film.getId());
        film.getGenres()
                .stream().distinct()
                .forEach(genre -> addGenreToFilm(film.getId(), genre.getId()));
        return findFilmById(film.getId()).get();
    }

    @Override
    public Optional<Film> findFilmById(int id) {
        String sql = "SELECT * FROM films LEFT JOIN mpa ON films.mpa_id = mpa.mpa_id WHERE id = ? ;";
        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, (rs, rowNum) -> mapRowToFilm(rs), id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Film> findAll() {
        String sql = "SELECT * FROM films LEFT JOIN mpa ON films.mpa_id = mpa.mpa_id;";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapRowToFilm(rs));
    }

    @Override
    public boolean addGenreToFilm(int filmId, int genreId) {
        String sql = "INSERT INTO film_genre(film_id, genre_id) " +
                "VALUES (?, ?)";
        return jdbcTemplate.update(sql, filmId, genreId) > 0;
    }

    @Override
    public boolean clearGenresFromFilm(int filmId) {
        String sql = "DELETE FROM film_genre WHERE film_id = ?";
        return jdbcTemplate.update(sql, filmId) > 0;
    }

    @Override
    public List<Integer> getLikesByFilm(int filmId) {
        String sql = "SELECT user_id FROM likes WHERE film_id =?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("user_id"), filmId);
    }

    @Override
    public boolean addLike(int filmId, int userId) {
        String sql = "INSERT INTO likes(film_id, user_id) " +
                "VALUES (?, ?)";
        return jdbcTemplate.update(sql, filmId, userId) > 0;
    }

    @Override
    public boolean deleteLike(int filmId, int userId) {
        String sql = "DELETE FROM likes WHERE (film_id = ? AND user_id = ?)";
        return jdbcTemplate.update(sql, filmId, userId) > 0;
    }

    private Film mapRowToFilm(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("films.name");
        String description = rs.getString("description");
        LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
        int duration = rs.getInt("duration");
        int mpaId = rs.getInt("mpa_id");
        String mpaName = rs.getString("mpa.name");

        Mpa mpa = Mpa.builder()
                .id(mpaId)
                .name(mpaName)
                .build();

        Film film = Film.builder()
                .id(id)
                .name(name)
                .description(description)
                .releaseDate(releaseDate)
                .duration(duration)
                .mpa(mpa)
                .build();
        film.getGenres().addAll(genreDbStorage.findGenresByFilmId(id));
        film.getLikes().addAll(getLikesByFilm(id));
        return film;
    }

    public Map<String, Object> toMap(Film film) {
        Map<String, Object> values = new HashMap<>();
        values.put("name", film.getName());
        values.put("description", film.getDescription());
        values.put("release_date", film.getReleaseDate());
        values.put("duration", film.getDuration());
        values.put("mpa_id", film.getMpa().getId());
        values.put("genres", film.getGenres());
        return values;
    }
}