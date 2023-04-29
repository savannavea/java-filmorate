package ru.yandex.practicum.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.annotation.NoWhitespaces;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class User {

    @JsonProperty
    private Integer id;

    @Email
    @NotEmpty
    @JsonProperty
    private String email;

    @NotEmpty
    @NoWhitespaces
    @JsonProperty
    private String login;

    @JsonProperty
    private String name;

    @PastOrPresent
    @JsonProperty
    private LocalDate birthday;

    private final Set<Integer> friends = new HashSet<>();

    public Set<Integer> getFriends() {
        return friends;
    }

    public void addFriend(int friendId) {
        friends.add(friendId);
    }

    public boolean deleteFriend(int friendId) {
        return friends.remove(friendId);
    }
}