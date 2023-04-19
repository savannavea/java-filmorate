package ru.yandex.practicum.validator;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.annotation.NoSpaces;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class LoginValidator implements ConstraintValidator<NoSpaces, String> {

    @Override
    public boolean isValid(String login, ConstraintValidatorContext context) {
        boolean notValid = login.contains(" ");
        if (notValid) {
            log.error("Login should not contain spaces {}", login);
        }
        return !notValid;
    }
}
