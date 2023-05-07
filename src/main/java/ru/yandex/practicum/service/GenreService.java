package ru.yandex.practicum.service;

import ru.yandex.practicum.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {

    Optional<Genre> getGenreById(int id);

    List<Genre> getAll();
}
