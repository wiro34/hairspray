package com.github.wiro34.hairspray.dummy_models;

import com.github.wiro34.hairspray.annotation.Factory;
import com.github.wiro34.hairspray.dummy_models.User.Sex;
import java.sql.Timestamp;

@Factory(User.class)
public class UserFactory {

    // 名前を返すメソッド
    public String name(User user) {
        // 他のメソッドに依存させるパターン
        return "John Doe (" + age(user) + ")";
    }

    // 年齢を返すメソッド
    public Integer age(User user) {
        return 18;
    }

    // 性別を返すメソッド
    public Sex sex(User user) {
        return Sex.MALE;
    }

    // 生成日時を返すメソッド
    public Timestamp createdAt(User user) {
        return new Timestamp(System.currentTimeMillis());
    }
}
