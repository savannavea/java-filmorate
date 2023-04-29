package ru.yandex.practicum.service;

import ru.yandex.practicum.model.Film;

import java.util.List;

public interface FilmService {

    Film createFilm(Film film);

    Film updateFilm(Film film);

    List<Film> findAllFilms();

    Film findFilmById(int id);

    void addLike(int userId, int filmId);

    void deleteLike(int userId, int filmId);

    List<Film> getTopFilms(int count);
}