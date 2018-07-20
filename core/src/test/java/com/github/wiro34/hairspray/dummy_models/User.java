package com.github.wiro34.hairspray.dummy_models;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private String mailAddress;
    private Integer age;
    private Sex sex;
    private boolean active;
    private LocalDateTime createdAt;

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public enum Sex {
        MALE, FEMALE
    }
}
