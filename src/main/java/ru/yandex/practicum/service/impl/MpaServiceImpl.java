package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.model.MPA;
import ru.yandex.practicum.service.MpaService;
import ru.yandex.practicum.storage.MpaStorage;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MpaServiceImpl implements MpaService {

    private final MpaStorage mpaStorage;

    @Override
    public MPA getMPAById(int id) {
        return mpaStorage.findMPAById(id)
                .orElseThrow(() -> new NotFoundException("MPA's id %d doesn't found!" + id));
    }

    @Override
    public List<MPA> getAll() {
        return mpaStorage.findAll();
    }
}
