package com.github.wiro34.hairspray;

import com.github.wiro34.hairspray.dummy_models.Post;
import com.github.wiro34.hairspray.dummy_models.User;
import com.github.wiro34.hairspray.dummy_models.User.Sex;
import java.math.BigInteger;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import static org.testng.AssertJUnit.*;
import org.testng.annotations.Test;

public class HairsprayTest extends AbstractCdiTest {

    @Inject
    private Hairspray factory;

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
        User user = factory.create(User.class, (u) -> {
            u.setFirstName("Jane");
        });
        assertEquals(user.getFullName(), "Jane Doe");
        assertEquals(user.getSex(), Sex.FEMALE);
    }

    @Test
    public void testCreate() {
        int count = count("post");
        Post post = factory.create(Post.class);
        assertNotNull(post);
        assertEquals(count("post"), count + 1);
    }
}
