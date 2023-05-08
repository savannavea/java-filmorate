package ru.yandex.practicum.service;

import ru.yandex.practicum.model.MPA;

import java.util.List;

public interface MpaService {

    MPA getMPAById(int id);

    List<MPA> getAll();
}
