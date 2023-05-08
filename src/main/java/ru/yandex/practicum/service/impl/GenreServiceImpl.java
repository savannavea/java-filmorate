package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.model.Genre;
import ru.yandex.practicum.service.GenreService;
import ru.yandex.practicum.storage.GenreStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreStorage genreStorage;

    @Override
    public Genre getGenreById(int id) {
        return genreStorage.findGenreById(id)
                .orElseThrow(() -> new NotFoundException("Genre's id %d doesn't found!" + id));
    }

    @Override
    public List<Genre> getAll() {
        return genreStorage.findAll().stream()
                .sorted(Comparator.comparingInt(Genre::getId))
                .collect(Collectors.toList());
    }
}
