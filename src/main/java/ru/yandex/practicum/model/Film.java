package ru.yandex.practicum.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.*;

@Data
@Builder
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

    @JsonProperty
    private Mpa mpa;

    @JsonProperty
    private final Set<Integer> likes = new HashSet<>();

    @JsonProperty
    private final List<Genre> genres = new ArrayList<>();
}