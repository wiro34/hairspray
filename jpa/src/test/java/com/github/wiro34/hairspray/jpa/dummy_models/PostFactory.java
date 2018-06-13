package com.github.wiro34.hairspray.jpa.dummy_models;

import com.github.wiro34.hairspray.Dynamic;
import com.github.wiro34.hairspray.jpa.HairsprayImpl;
import com.github.wiro34.hairspray.annotation.Factory;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.function.Function;
import javax.inject.Inject;

@Factory(Post.class)
public class PostFactory {

    @Inject
    private HairsprayImpl factory;

    // TODO: replace to association
    public Dynamic<Post, User> user = (post) -> factory.create(User.class);

    public String subject = "Example post";

    public String content = "it is content";

    public Function<Post, Timestamp> createdAt = (post) -> {
        Calendar c = Calendar.getInstance();
        c.set(2016, 1, 1, 0, 0, 0);
        return new Timestamp(c.getTimeInMillis());
    };
}
