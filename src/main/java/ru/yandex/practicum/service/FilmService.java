package ru.yandex.practicum.service;

import ru.yandex.practicum.model.Film;

import java.util.List;

public interface FilmService {

    Film create(Film film);

    Film update(Film film);

    List<Film> findAll();
}