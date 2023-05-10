package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.model.Film;
import ru.yandex.practicum.service.FilmService;
import ru.yandex.practicum.storage.FilmStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FilmServiceImpl implements FilmService {

    private final FilmStorage filmStorage;

    @Override
    public Film create(Film film) {
        filmStorage.create(film);
        return film;
    }

    @Override
    public Film update(Film film) {
        filmStorage
                .findFilmById(film.getId())
                .orElseThrow(() -> new NotFoundException("Not found film by id: " + film.getId()));

        return filmStorage.update(film);
    }

    @Override
    public List<Film> getAll() {
        return filmStorage.findAll();
    }

    @Override
    public Film getFilmById(int id) {
        return filmStorage
                .findFilmById(id)
                .orElseThrow(() -> new NotFoundException("Film's id %d doesn't found!" + id));

    }

    @Override
    public void addLike(int userId, int filmId) {
        filmStorage.addLike(filmId, userId);
    }

    @Override
    public void deleteLike(int userId, int filmId) {
        boolean isDeleted = filmStorage.deleteLike(filmId, userId);
        if (!isDeleted) {
            throw new NotFoundException("Not found");
        }
    }

    @Override
    public List<Film> getTopFilms(int count) {
        return filmStorage
                .findAll()
                .stream()
                .sorted(Comparator.comparingInt(o -> o.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
    }
}