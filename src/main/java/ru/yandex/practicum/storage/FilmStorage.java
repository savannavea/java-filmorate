package ru.yandex.practicum.storage;

import ru.yandex.practicum.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {
    Film create(Film film);

    Film update(Film film);

    Optional<Film> findFilmById(int id);

    List<Film> findAll();

    boolean addGenreToFilm(int filmId, int genreId);

    boolean clearGenresFromFilm(int filmId);

    List<Integer> getLikesByFilm(int filmId);

    boolean addLike(int filmId, int userId);

    boolean deleteLike(int filmId, int userId);

}