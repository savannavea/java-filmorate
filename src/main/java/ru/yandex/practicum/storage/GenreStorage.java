package ru.yandex.practicum.storage;

import ru.yandex.practicum.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreStorage {

    Optional<Genre> findGenreById(int id);

    List<Genre> findAll();

    List<Genre> findGenresByFilmId(int filmId);
}
