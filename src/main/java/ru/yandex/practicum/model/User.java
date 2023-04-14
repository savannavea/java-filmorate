package ru.yandex.practicum.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import ru.yandex.practicum.annotation.NoSpaces;

import java.time.LocalDate;

@Data
public class User {

    @JsonProperty
    private Integer id;

    @Email
    @NotEmpty
    @JsonProperty
    private String email;

    @NotEmpty
    @NoSpaces
    @JsonProperty
    private String login;

    @NotNull
    @JsonProperty
    private String name;

    @PastOrPresent
    @JsonProperty
    private LocalDate birthday;
}
