package com.github.wiro34.hairspray.example;

import com.github.wiro34.hairspray.annotation.Factory;
import com.github.wiro34.hairspray.example.User.Sex;
import java.sql.Timestamp;
import java.util.function.Function;

@Factory(User.class)
public class UserFactory {

    // Simple value
    public String firstName = "John";

    public String lastName = "Doe";

    public Integer age = 18;

    // Lazy value
    public Function<User, Sex> sex = (user) -> user.getFirstName().equals("Jane") ? Sex.FEMALE : Sex.MALE;

    public Function<User, Timestamp> createdAt = (user) -> new Timestamp(System.currentTimeMillis());
}
