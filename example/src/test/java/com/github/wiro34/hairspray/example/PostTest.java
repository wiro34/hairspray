package com.github.wiro34.hairspray.example;

import com.github.wiro34.hairspray.Hairspray;
import javax.inject.Inject;
import static org.testng.AssertJUnit.assertEquals;
import org.testng.annotations.Test;

public class PostTest extends AbstractCdiTest {

    @Inject
    private Hairspray factory;

    @Test
    public void testGetAuther() throws Exception {
        Post post = factory.create(Post.class);
        assertEquals(post.getAuther().getFirstName(), "John");
    }
}
