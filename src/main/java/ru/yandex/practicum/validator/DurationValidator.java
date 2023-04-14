package ru.yandex.practicum.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.annotation.Positive;

import java.time.Duration;

@Slf4j
public class DurationValidator implements ConstraintValidator<Positive, Duration> {

    @Override
    public boolean isValid(Duration duration, ConstraintValidatorContext context) {
        boolean notValid = duration.isNegative();
        if (notValid) {
            log.error("Duration should be more 0 {}", duration);
        }
        return !notValid;
    }
}
