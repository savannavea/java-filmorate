package ru.yandex.practicum.manager.impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.manager.InMemoryUserManager;
import ru.yandex.practicum.model.User;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class InMemoryUserManagerImpl implements InMemoryUserManager {

    private final Map<Integer, User> users = new ConcurrentHashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    @Override
    public User create(User user) {
        Integer userId = idGenerator.getAndIncrement();
        user.setId(userId);
        users.put(userId, user);
        return user;
    }

    @Override
    public User update(User user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public Collection<User> findAll() {
        return users.values();
    }
}