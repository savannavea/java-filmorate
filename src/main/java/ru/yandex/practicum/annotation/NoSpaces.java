package ru.yandex.practicum.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.yandex.practicum.validator.LoginValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LoginValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoSpaces {
    String message() default "Spaces prohibited";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
