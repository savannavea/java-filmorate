package ru.yandex.practicum.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.yandex.practicum.annotation.AfterSpecialDate;
import ru.yandex.practicum.annotation.Positive;

import javax.validation.constraints.NotEmpty;
import java.time.Duration;
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
    @AfterSpecialDate
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate releaseDate;

    @JsonProperty
    @Positive
    private Duration duration;
}
