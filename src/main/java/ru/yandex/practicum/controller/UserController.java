package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.exception.BadRequestException;
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

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody User user) {
        log.info("Got request to create user {}", user);
        if (user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new BadRequestException("Email cannot be empty and must contain @");
        }
        if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            throw new BadRequestException("Login cannot contain spaces");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new BadRequestException("Birthday can't be in the future");
        }
        setLoginAsNameIfEmpty(user);
        return ResponseEntity.ok(userService.createUser(user));
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
        return userService.updateUser(user);
    }

    @GetMapping
    public List<User> findAll() {
        return userService.findAllUsers();
    }

    @GetMapping("/{id}")
    public User findUserById(@PathVariable int id) {
        return userService.findUserById(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public void addToFriends(@PathVariable int id, @PathVariable int friendId) {
        log.info("User added to the friends: {}", friendId);
        userService.addToFriends(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFriends(@PathVariable int id, @PathVariable int friendId) {
        log.info("User removed from the friends: {}", friendId);
        userService.deleteFromFriends(id, friendId);
    }

    @GetMapping("/{id}/friends")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getFriendsList(@PathVariable int id) {
        return userService.getFriendsList(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getFriendsCommonList(@PathVariable int id, @PathVariable int otherId) {
        return userService.getCommonFriends(id, otherId);
    }

    private void setLoginAsNameIfEmpty(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}