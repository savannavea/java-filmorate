package ru.yandex.practicum.storage;

import ru.yandex.practicum.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {
    Film create(Film film);

    Film update(Film film);

    Optional<Film> findFilmById(int id);

    List<Film> findAll();

    List<Film> listFilms();

}