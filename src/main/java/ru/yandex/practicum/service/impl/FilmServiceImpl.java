package ru.yandex.practicum.service.impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.model.Film;
import ru.yandex.practicum.service.FilmService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class FilmServiceImpl implements FilmService {

    private final Map<Integer, Film> films = new ConcurrentHashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    @Override
    public Film create(Film film) {
        Integer filmId = idGenerator.getAndIncrement();
        film.setId(filmId);
        films.put(filmId, film);
        return film;
    }

    @Override
    public Film update(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new NotFoundException("User  does not exist");
        }
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public List<Film> findAll() {
        return new ArrayList<>(films.values());
    }
}