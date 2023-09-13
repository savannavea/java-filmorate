package ru.yandex.practicum.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Friendship {

    @JsonProperty
    private Integer userId;

    @JsonProperty
    private Integer friendId;

    @JsonProperty
    private boolean status;
}
