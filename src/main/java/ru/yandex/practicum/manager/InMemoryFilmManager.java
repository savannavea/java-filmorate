package ru.yandex.practicum.manager;

import ru.yandex.practicum.model.Film;

import java.util.Collection;

public interface InMemoryFilmManager {

    Film save(Film film);

    Film update(Integer id, Film film);

    Collection<Film> findAll();

}
