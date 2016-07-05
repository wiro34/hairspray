package com.github.wiro34.hairspray.example;

import com.github.wiro34.hairspray.Hairspray;
import com.github.wiro34.hairspray.annotation.Factory;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Optional;
import java.util.function.Function;
import javax.inject.Inject;

@Factory(Post.class)
public class PostFactory {

    @Inject
    private Hairspray factory;

    @Inject
    private UserRepository userRepository;

    public Function<Post, User> auther = (post) -> {
        Optional<User> userOption = userRepository.find(1L);
        return userOption.orElse(factory.create(User.class));
    };

    public String subject = "Example post";

    public String content = "it is content";

    public Function<Post, Timestamp> createdAt = (post) -> {
        Calendar c = Calendar.getInstance();
        c.set(2016, 1, 1, 0, 0, 0);
        return new Timestamp(c.getTimeInMillis());
    };
}
