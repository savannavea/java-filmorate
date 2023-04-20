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
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new FilmInformationException("Release date - no earlier than December 28, 1895");
        }
        if (film.getDuration() < 0) {
            throw new FilmInformationException("Movie duration must be positive");
        }
        return ResponseEntity.ok(manager.save(film));
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.info("Got request to update film {} ", film);
        return manager.update(film);
    }

    @GetMapping
    public Collection<Film> findAll() {
        return manager.findAll();
    }
}
