package ru.yandex.practicum.annotation;

import ru.yandex.practicum.validator.LoginValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
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
