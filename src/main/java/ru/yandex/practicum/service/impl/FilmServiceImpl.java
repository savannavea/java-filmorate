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
        filmStorage.createFilm(film);
        return film;
    }

    @Override
    public Film update(Film film) {
        if (!filmStorage.findAllId().contains(film.getId())) {
            throw new NotFoundException("Not found user by id: " + film.getId());
        }
        filmStorage.updateFilm(film);
        return film;
    }

    @Override
    public List<Film> getAll() {
        return filmStorage.findFilmList();
    }

    @Override
    public Film getFilmById(int id) {
        if (!filmStorage.findAllId().contains(id)) {
            throw new NotFoundException(String.format("Film's id %d doesn't found!", id));
        }
        return filmStorage.findFilmById(id);
    }

    @Override
    public void addLike(int userId, int filmId) {
        User user = userStorage.findUserById(userId);
        Film film = filmStorage.findFilmById(filmId);
        film.addLike(user);
    }

    @Override
    public void deleteLike(int userId, int filmId) {
        User user = userStorage.findUserById(userId);
        Film film = filmStorage.findFilmById(filmId);
        film.deleteLike(user);
    }

    @Override
    public List<Film> getTopFilms(int count) {
        List<Film> sortedByLikesFilms = filmStorage.findFilmList();
        sortedByLikesFilms.sort((Comparator.comparingInt(o -> o.getLikes().size())));
        return sortedByLikesFilms;
    }
}