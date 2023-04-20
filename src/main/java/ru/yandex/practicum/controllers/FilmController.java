package ru.yandex.practicum.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.exception.FilmInformationException;
import ru.yandex.practicum.manager.InMemoryFilmManager;
import ru.yandex.practicum.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "films", produces = MediaType.APPLICATION_JSON_VALUE)
public class FilmController {

    private final InMemoryFilmManager manager;

    @PostMapping
    public ResponseEntity<Film> save(@Valid @RequestBody Film film) {
        log.info("Got request to create film {}", film);
        /*if (film.getDuration().isNegative() || film.getDuration().isZero()) {
            throw new FilmInformationException("Duration must be positive");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new FilmInformationException("Release date - no earlier than December 28, 1895");
        }*/
        if (film.getName().isBlank() || film.getName() == null) {
            throw new FilmInformationException("Название не может быть пустым");
        }
        if (film.getDescription().length() > 200) {
            throw new FilmInformationException("Максимальная длина описания — 200 символов");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new FilmInformationException("Дата релиза — не раньше 28 декабря 1895 года");
        }
        if (film.getDuration().isNegative() || film.getDuration().isZero()) {
            throw new FilmInformationException("Продолжительность фильма должна быть положительной");
        }
        return ResponseEntity.ok(manager.save(film));
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.info("Got request to update film {} ", film);
        /*if (film.getDuration().isNegative() || film.getDuration().isZero()) {
            throw new FilmInformationException("Duration must be positive");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new FilmInformationException("Release date - no earlier than December 28, 1895");
        }*/
        return manager.update(film);
    }

    @GetMapping
    public Collection<Film> findAll() {
        return manager.findAll();
    }
}