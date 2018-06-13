package com.github.wiro34.hairspray.dummy_models;

import com.github.wiro34.hairspray.Dynamic;
import com.github.wiro34.hairspray.Hairspray;
import com.github.wiro34.hairspray.annotation.Factory;

import java.time.LocalDateTime;
import java.util.function.Function;

@Factory(Post.class)
public class PostFactory {

    private Hairspray factory = new Hairspray();

    // TODO: replace to association
    public Dynamic<Post, User> user = (post) -> factory.build(User.class);

    public String subject = "Example post";

    public String content = "it is content";

    public Function<Post, LocalDateTime> createdAt = (post) -> {
        return LocalDateTime.of(2016, 1, 1, 0, 0, 0);
    };
}
