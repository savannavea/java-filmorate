package ru.yandex.practicum.service;

import ru.yandex.practicum.model.Genre;

import java.util.List;

public interface GenreService {

    Genre getById(int id);

    List<Genre> getAll();
}
