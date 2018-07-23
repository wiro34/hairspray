package com.github.wiro34.hairspray.dummy_models;

import com.github.wiro34.hairspray.Dynamic;
import com.github.wiro34.hairspray.Lazy;
import com.github.wiro34.hairspray.annotation.Factory;
import com.github.wiro34.hairspray.dummy_models.User.Sex;

import java.time.LocalDateTime;

@Factory(User.class)
public class UserFactory {

    public String firstName = "John";

    public String lastName = "Doe";

    public Integer age = 18;

    public boolean active = true;

    public Lazy<User, Sex> sex = (user) -> user.getFirstName().equals("Jane") ? Sex.FEMALE : Sex.MALE;

    public Dynamic<User, LocalDateTime> createdAt = (user) -> LocalDateTime.of(2112, 1, 2, 3, 4, 56, 0);

}
