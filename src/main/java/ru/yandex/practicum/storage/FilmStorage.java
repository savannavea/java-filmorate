package ru.yandex.practicum.storage;

import ru.yandex.practicum.model.Film;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface FilmStorage {
    Film createFilm(Film film);

    Film updateFilm(Film film);

    void deleteAllFilms();

    Optional<Film> findFilmById(int id);

    List<Film> findFilmList();

    Set<Integer> findAllId();
}