package com.github.wiro34.hairspray.dummy_models;

import com.github.wiro34.hairspray.annotation.Factory;
import com.github.wiro34.hairspray.dummy_models.User.Sex;
import java.sql.Timestamp;
import java.util.function.Function;

@Factory(User.class)
public class UserFactory {

    // Simple value
    public String firstName = "John";

    public String lastName = "Doe";

    public Integer age = 18;

    public boolean active = true;

    // Lazy value
    public Function<User, Sex> sex = (user) -> user.getFirstName().equals("Jane") ? Sex.FEMALE : Sex.MALE;

    public Function<User, Timestamp> createdAt = (user) -> new Timestamp(System.currentTimeMillis());
}
