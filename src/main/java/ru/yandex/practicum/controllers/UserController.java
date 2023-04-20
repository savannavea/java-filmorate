package ru.yandex.practicum.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.manager.InMemoryUserManager;
import ru.yandex.practicum.model.User;

import javax.validation.Valid;
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

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.info("Got request to update user {} ", user);
        setLoginAsNameIfEmpty(user);
        return manager.update(user);
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
