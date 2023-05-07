package ru.yandex.practicum.service;

import ru.yandex.practicum.model.MPA;

import java.util.List;
import java.util.Optional;

public interface MpaService {

    Optional<MPA> getMPAById(int id);

    List<MPA> getAll();
}
