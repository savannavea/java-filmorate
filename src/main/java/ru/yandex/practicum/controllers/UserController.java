package ru.yandex.practicum.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.manager.InMemoryUserManager;
import ru.yandex.practicum.model.User;

import java.util.Collection;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final InMemoryUserManager manager;

    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody User user) {
        log.info("Got request to create user {}", user);
        setLoginAsNameIfEmpty(user);
        return ResponseEntity.ok(manager.create(user));
    }

    @PutMapping("{id}")
    public User update(@PathVariable Integer id, @Valid @RequestBody User user) {
        log.info("Got request to update user {} with id '{}'", user, id);
        setLoginAsNameIfEmpty(user);
        return manager.update(id, user);
    }

    @GetMapping
    public Collection<User> findAll() {
        return manager.findAll();
    }

    private void setLoginAsNameIfEmpty(User user) {
        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
    }
}