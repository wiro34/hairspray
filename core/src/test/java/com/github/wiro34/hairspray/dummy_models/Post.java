package com.github.wiro34.hairspray.dummy_models;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Post {

    private Long id;
    private User user;
    private String subject;
    private String content;
    private LocalDateTime createdAt;

}
