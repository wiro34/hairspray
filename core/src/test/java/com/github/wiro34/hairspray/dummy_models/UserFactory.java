package com.github.wiro34.hairspray.dummy_models;

import com.github.wiro34.hairspray.Dynamic;
import com.github.wiro34.hairspray.Lazy;
import com.github.wiro34.hairspray.annotation.Factory;
import com.github.wiro34.hairspray.dummy_models.User.Sex;

import java.time.LocalDateTime;

@Factory(User.class)
public class UserFactory {

    // Simple value
    public String firstName = "John";

    public String lastName = "Doe";

    public Integer age = 18;

    public boolean active = true;

    // Lazy value
    public Lazy<User, Sex> sex = (user) -> user.getFirstName().equals("Jane") ? Sex.FEMALE : Sex.MALE;

    // Dynamic value
    public Dynamic<User, LocalDateTime> createdAt = (user) -> LocalDateTime.of(2112, 1, 2, 3, 4, 56, 0);
}
