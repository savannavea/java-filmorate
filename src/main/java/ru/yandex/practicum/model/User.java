package ru.yandex.practicum.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.yandex.practicum.annotation.NoSpaces;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
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

    @JsonProperty
    private String name;

    @PastOrPresent
    @JsonProperty
    private LocalDate birthday;
}
