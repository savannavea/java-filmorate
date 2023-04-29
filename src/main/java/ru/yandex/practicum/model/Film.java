package ru.yandex.practicum.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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

    private final Set<Integer> likes = new HashSet<>();

    public void addLike(User user) {
        likes.add(user.getId());
    }

    public void deleteLike(User user) {
        likes.remove(user.getId());
    }

}