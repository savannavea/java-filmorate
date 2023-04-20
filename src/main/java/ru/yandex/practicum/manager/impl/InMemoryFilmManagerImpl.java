package ru.yandex.practicum.manager.impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.exception.UserInformationException;
import ru.yandex.practicum.manager.InMemoryFilmManager;
import ru.yandex.practicum.model.Film;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class InMemoryFilmManagerImpl implements InMemoryFilmManager {

    private final Map<Integer, Film> films = new ConcurrentHashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    @Override
    public Film save(Film film) {
        Integer filmId = idGenerator.getAndIncrement();
        film.setId(filmId);
        films.put(filmId, film);
        return film;
    }

    @Override
    public Film update(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new UserInformationException("User  does not exist");
        }
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Collection<Film> findAll() {
        return films.values();
    }

}
