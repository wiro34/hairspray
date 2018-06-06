package com.github.wiro34.hairspray.dummy_models;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "mail_address")
    private String mailAddress;

    private Integer age;

    private Sex sex;

    private boolean active;

    @Column(name = "created_at")
    private Timestamp createdAt;

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public static enum Sex {
        MALE, FEMALE
    }
}
