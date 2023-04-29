package ru.yandex.practicum.storage.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.exception.NotFoundException;
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
        if (!films.containsKey(film.getId())) {
            throw new NotFoundException("Not found user by id: " + film.getId());
        }
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public void deleteAllFilms() {
        films.clear();
    }

    @Override
    public Film findFilmById(int id) {
        if (!films.containsKey(id)) {
            throw new NotFoundException(String.format("Film's id %d doesn't found!", id));
        }
        return films.get(id);
    }

    @Override
    public List<Film> getFilmList() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Set<Integer> getAllId() {
        return films.keySet();
    }
}
