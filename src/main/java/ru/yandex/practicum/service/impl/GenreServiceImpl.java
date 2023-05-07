package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.model.Genre;
import ru.yandex.practicum.service.GenreService;
import ru.yandex.practicum.storage.GenreStorage;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreStorage genreStorage;

    @Override
    public Optional<Genre> getGenreById(int id) {
        return genreStorage.findGenreById(id);
    }

    @Override
    public List<Genre> getAll() {
        return genreStorage.findAll();
    }
}
