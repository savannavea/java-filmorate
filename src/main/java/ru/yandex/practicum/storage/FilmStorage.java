package ru.yandex.practicum.storage;

import ru.yandex.practicum.model.Film;

import java.util.List;
import java.util.Set;

public interface FilmStorage {
    Film createFilm(Film film);

    Film updateFilm(Film film);

    void deleteAllFilms();

    Film findFilmById(int id);

    List<Film> getFilmList();

    Set<Integer> getAllId();
}