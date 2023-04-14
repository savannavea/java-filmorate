package ru.yandex.practicum.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.yandex.practicum.validator.DurationValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DurationValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Positive {

    String message() default "Invalid duration";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
