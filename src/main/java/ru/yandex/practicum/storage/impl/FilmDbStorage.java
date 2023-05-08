package ru.yandex.practicum.storage.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.model.Film;
import ru.yandex.practicum.model.MPA;
import ru.yandex.practicum.storage.FilmStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
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
                .usingGeneratedKeyColumns("film_id");
        int id = simpleJdbcInsert.executeAndReturnKey(film.toMap()).intValue();
        film.setId(id);
        film.getGenres().forEach(genre -> addGenreToFilm(id, genre.getId()));
        return film;
    }

    @Override
    public Film update(Film film) {
        String sql = "update films set name = ?, description = ?, release_date = ?, " +
                "duration = ?, mpa_id = ? where film_id = ?";

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
        String sql = "select * from films left join mpa on films.mpa_id = mpa.mpa_id where film_id = ? ;";
        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, (rs, rowNum) -> mapRowToFilm(rs), id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Film> findAll() {
        String sql = "select * from films left join mpa on films.mpa_id = mpa.mpa_id;";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapRowToFilm(rs));
    }

    @Override
    public boolean addGenreToFilm(int filmId, int genreId) {
        String sql = "insert into film_genre(film_id, genre_id) " +
                "values (?, ?)";
        return jdbcTemplate.update(sql, filmId, genreId) > 0;
    }

    @Override
    public boolean clearGenresFromFilm(int filmId) {
        String sql = "delete from film_genre where film_id = ?";
        return jdbcTemplate.update(sql, filmId) > 0;
    }

    @Override
    public List<Integer> getLikesByFilm(int filmId) {
        String sql = "select user_id from likes where film_id =?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("user_id"), filmId);
    }

    @Override
    public boolean addLike(int filmId, int userId) {
        String sql = "insert into likes(film_id, user_id) " +
                "values (?, ?)";
        return jdbcTemplate.update(sql, filmId, userId) > 0;
    }

    @Override
    public boolean deleteLike(int filmId, int userId) {
        String sql = "delete from likes where (film_id = ? AND user_id = ?)";
        return jdbcTemplate.update(sql, filmId, userId) > 0;
    }

    private Film mapRowToFilm(ResultSet rs) throws SQLException {
        int id = rs.getInt("film_id");
        String name = rs.getString("films.name");
        String description = rs.getString("description");
        LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
        int duration = rs.getInt("duration");
        int mpaId = rs.getInt("mpa_id");
        String mpaName = rs.getString("mpa.name");

        MPA mpa = MPA.builder()
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
}