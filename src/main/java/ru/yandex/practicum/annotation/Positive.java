package ru.yandex.practicum.annotation;

import ru.yandex.practicum.validator.DurationValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
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
