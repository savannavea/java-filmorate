package ru.yandex.practicum.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Genre {
    @JsonProperty
    private Integer id;

    @JsonProperty
    private String name;
}