package com.github.wiro34.hairspray.jpa;

import com.github.wiro34.hairspray.jpa.dummy_models.Post;
import com.github.wiro34.hairspray.jpa.dummy_models.User;
import com.github.wiro34.hairspray.jpa.dummy_models.User.Sex;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.math.BigInteger;
import java.util.List;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

public class HairsprayImplTest extends ArquillianTest {

    @Inject
    private HairsprayImpl factory;

    @Inject
    private EntityManager entityManager;

    private int count(String tableName) {
        return ((BigInteger) entityManager.createNativeQuery("select count(1) from " + tableName).getSingleResult()).intValue();
    }

    @Test
    public void testBuild() {
        int count = count("user");
        User user = factory.build(User.class);
        assertNotNull(user);
        assertEquals(count("user"), count);
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

    @Test
    public void testCreate() {
        int count = count("post");
        Post post = factory.create(Post.class);
        assertNotNull(post);
        assertEquals(count("post"), count + 1);
    }
}
