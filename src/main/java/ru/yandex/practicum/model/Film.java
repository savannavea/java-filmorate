package ru.yandex.practicum.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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

    @JsonProperty
    private MPA mpa;
    @JsonProperty
    private final Set<Integer> likes = new HashSet<>();

    @JsonProperty
    private Set<Genre> genres = new HashSet<>();

    public void addLike(User user) {
        likes.add(user.getId());
    }

    public void deleteLike(User user) {
        likes.remove(user.getId());
    }

    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("name", name);
        values.put("description", description);
        values.put("release_date", releaseDate);
        values.put("duration", duration);
        values.put("mpa_id", mpa.getId());
        return values;
    }
}