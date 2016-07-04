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

    private String name;

    @Column(name = "mail_address")
    private String mailAddress;

    private Integer age;

    private Sex sex;

    @Column(name = "created_at")
    private Timestamp createdAt;

    public static enum Sex {
        MALE, FEMALE
    }
}