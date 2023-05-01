package ru.yandex.practicum.storage.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.model.Film;
import ru.yandex.practicum.storage.FilmStorage;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Integer, Film> films = new HashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    @Override
    public Film createFilm(Film film) {
        Integer filmId = idGenerator.getAndIncrement();
        film.setId(filmId);
        films.put(filmId, film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public void deleteAllFilms() {
        films.clear();
    }

    @Override
    public Optional<Film> findFilmById(int id) {
        return Optional.ofNullable(films.get(id));
    }

    @Override
    public List<Film> findFilmList() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Set<Integer> findAllId() {
        return films.keySet();
    }
}
