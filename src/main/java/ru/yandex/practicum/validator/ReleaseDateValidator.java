package ru.yandex.practicum.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.annotation.AfterSpecialDate;

import java.time.LocalDate;

@Slf4j
public class ReleaseDateValidator implements ConstraintValidator<AfterSpecialDate, LocalDate> {

    @Override
    public boolean isValid(LocalDate releaseDate, ConstraintValidatorContext context) {
        boolean isValid = releaseDate.isAfter(LocalDate.of(1895, 12, 28));
        if (!isValid) {
            log.error("Release date should be after 28.12.1895, but is {}", releaseDate);
        }
        return isValid;
    }
}
