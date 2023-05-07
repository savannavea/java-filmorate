package ru.yandex.practicum.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MPA {
    @JsonProperty
    private Integer id;

    @JsonProperty
    private String name;
}
