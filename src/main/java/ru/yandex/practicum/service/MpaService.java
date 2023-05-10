package ru.yandex.practicum.service;

import ru.yandex.practicum.model.Mpa;

import java.util.List;

public interface MpaService {

    Mpa getById(int id);

    List<Mpa> getAll();
}
