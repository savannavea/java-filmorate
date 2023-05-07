package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.model.MPA;
import ru.yandex.practicum.service.MpaService;
import ru.yandex.practicum.storage.MpaStorage;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MpaServiceImpl implements MpaService {

    MpaStorage mpaStorage;

    @Override
    public Optional<MPA> getMPAById(int id) {
        return mpaStorage.findMPAById(id);
    }

    @Override
    public List<MPA> getAll() {
        return mpaStorage.findAll();
    }
}
