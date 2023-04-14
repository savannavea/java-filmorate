package ru.yandex.practicum.manager.impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.manager.InMemoryFilmManager;
import ru.yandex.practicum.model.Film;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class InMemoryFilmManagerImpl implements InMemoryFilmManager {

    private final Map<Integer, Film> films = new ConcurrentHashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(0);

    @Override
    public Film save(Film film) {
        Integer filmId = idGenerator.getAndIncrement();
        film.setId(filmId);
        return upsert(filmId, film);
    }

    @Override
    public Film update(Integer id, Film film) {
        return upsert(id, film);
    }

    @Override
    public Collection<Film> findAll() {
        return films.values();
    }

    private Film upsert(Integer id, Film film) {
        films.put(id, film);
        return films.get(id);
    }

}
