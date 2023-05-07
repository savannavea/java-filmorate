package ru.yandex.practicum.storage;

import ru.yandex.practicum.model.MPA;

import java.util.List;
import java.util.Optional;

public interface MpaStorage {

    Optional<MPA> findMPAById(int id);

    List<MPA> findAll();
}
