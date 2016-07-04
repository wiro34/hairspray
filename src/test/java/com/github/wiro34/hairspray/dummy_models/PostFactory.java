package com.github.wiro34.hairspray.dummy_models;

import com.github.wiro34.hairspray.Hairspray;
import com.github.wiro34.hairspray.annotation.Factory;
import java.sql.Timestamp;
import java.util.Optional;
import javax.inject.Inject;

@Factory(Post.class)
public class PostFactory {

    @Inject
    private Hairspray factory;

    @Inject
    private UserRepository userRepository;

    // 名前を返すメソッド
    public User user(Post post) {
        // 関連するエンティティも同時に生成
        Optional<User> user = userRepository.find(1L);
        return user.orElse(factory.create(User.class));
    }

    // 年齢を返すメソッド
    public String subject(Post post) {
        return "Example post";
    }

    public String content(Post post) {
        return "it is content";
    }

    public Timestamp createdAt(Post post) {
        return new Timestamp(System.currentTimeMillis());
    }
}
