package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.model.Film;
import ru.yandex.practicum.model.User;
import ru.yandex.practicum.service.FilmService;
import ru.yandex.practicum.storage.FilmStorage;
import ru.yandex.practicum.storage.UserStorage;

import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FilmServiceImpl implements FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Override
    public Film create(Film film) {
        filmStorage.create(film);
        return film;
    }

    @Override
    public Film update(Film film) {
        Film filmById = filmStorage.findFilmById(film.getId())
                .orElseThrow(() -> new NotFoundException("Not found film by id: " + film.getId()));

        return filmStorage.update(film);
    }

    @Override
    public List<Film> getAll() {
        return filmStorage.findAll();
    }

    @Override
    public Film getFilmById(int id) {
        Film filmById = filmStorage.findFilmById(id)
                .orElseThrow(() -> new NotFoundException("Film's id %d doesn't found!" + id));

        return filmById;
    }

    @Override
    public void addLike(int userId, int filmId) {
        User user = userStorage.findUserById(userId).orElseThrow(() -> new NotFoundException("User's id %d doesn't found!" + userId));
        Film film = filmStorage.findFilmById(filmId)
                .orElseThrow(() -> new NotFoundException("Film's id %d doesn't found!" + filmId));
        film.addLike(user);
    }

    @Override
    public void deleteLike(int userId, int filmId) {
        User user = userStorage.findUserById(userId).orElseThrow(() -> new NotFoundException("User's id %d doesn't found!" + userId));
        Film film = filmStorage.findFilmById(filmId)
                .orElseThrow(() -> new NotFoundException("Film's id %d doesn't found!" + filmId));
        film.deleteLike(user);
    }

    @Override
    public List<Film> getTopFilms(int count) {
        List<Film> sortedByLikesFilms = filmStorage.findAll();
        sortedByLikesFilms.sort((Comparator.comparingInt(o -> o.getLikes().size())));
        return sortedByLikesFilms;
    }
}