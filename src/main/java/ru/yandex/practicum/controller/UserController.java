package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.exception.InformationException;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.model.User;
import ru.yandex.practicum.service.UserService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService manager;

    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody User user) {
        log.info("Got request to create user {}", user);
        if (user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new InformationException("Email cannot be empty and must contain @");
        }
        if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            throw new InformationException("Login cannot contain spaces");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new InformationException("Birthday can't be in the future");
        }
        setLoginAsNameIfEmpty(user);
        return ResponseEntity.ok(manager.create(user));
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.info("Got request to update user {}", user);
        if (user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new NotFoundException("Email cannot be empty and must contain @");
        }
        if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            throw new NotFoundException("Login cannot contain spaces");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new NotFoundException("Birthday should be in the future");
        }
        setLoginAsNameIfEmpty(user);
        return manager.update(user);
    }

    @GetMapping
    public List<User> findAll() {
        return manager.findAll();
    }

    private void setLoginAsNameIfEmpty(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}