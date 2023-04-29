package ru.yandex.practicum.storage.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.model.User;
import ru.yandex.practicum.storage.UserStorage;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Integer, User> users = new HashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);


    @Override
    public User createUser(User user) {
        Integer userId = idGenerator.getAndIncrement();
        user.setId(userId);
        users.put(userId, user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (!users.containsKey(user.getId())) {
            throw new NotFoundException("User  does not exist");
        }
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public void deleteAllUsers() {
        users.clear();
    }

    @Override
    public User findUserById(int id) {
        if (!users.containsKey(id)) {
            throw new NotFoundException(String.format("User's id %d doesn't found!", id));
        }
        return users.get(id);
    }

    @Override
    public List<User> getUserList() {
        return new ArrayList<>(users.values());
    }

    @Override
    public Set<Integer> getAllId() {
        return users.keySet();
    }
}
