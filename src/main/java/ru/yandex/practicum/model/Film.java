package ru.yandex.practicum.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
public class Film {

    @JsonProperty
    private Integer id;

    @NotEmpty
    @JsonProperty
    private String name;

    @Length(max = 200)
    @JsonProperty
    private String description;

    @JsonProperty
    private LocalDate releaseDate;

    @JsonProperty
    private Integer duration;
}
