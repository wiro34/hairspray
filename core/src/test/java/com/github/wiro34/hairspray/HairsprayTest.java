package com.github.wiro34.hairspray;

import com.github.wiro34.hairspray.dummy_models.Post;
import com.github.wiro34.hairspray.dummy_models.User;
import com.github.wiro34.hairspray.dummy_models.User.Sex;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

public class HairsprayTest {

    private Hairspray factory = new Hairspray();

    @Test
    public void testBuild() {
        User user = factory.build(User.class);
        assertNotNull(user);
    }

    @Test
    public void testBuildWithInitializer() {
        User user = factory.build(User.class, (u) -> {
            u.setFirstName("Jane");
        });
        assertEquals(user.getFullName(), "Jane Doe");
        assertEquals(user.getSex(), Sex.FEMALE);
    }

    @Test
    public void testBuildListWithInitializer() {
        List<User> users = factory.buildList(User.class, 3, (u, n) -> {
            u.setFirstName("Agent");
            u.setLastName("Smith #" + (n + 1));
        });
        assertEquals(users.size(), 3);
        assertEquals(users.get(0).getFullName(), "Agent Smith #1");
        assertEquals(users.get(1).getFullName(), "Agent Smith #2");
        assertEquals(users.get(2).getFullName(), "Agent Smith #3");
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testCreate() {
        factory.create(Post.class);
    }
}
