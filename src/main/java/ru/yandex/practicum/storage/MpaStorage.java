package ru.yandex.practicum.storage;

import ru.yandex.practicum.model.Mpa;

import java.util.List;
import java.util.Optional;

public interface MpaStorage {

    Optional<Mpa> findMpaById(int id);

    List<Mpa> findAll();
}