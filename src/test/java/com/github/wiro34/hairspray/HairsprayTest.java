package com.github.wiro34.hairspray;

import com.github.wiro34.hairspray.dummy_models.Post;
import com.github.wiro34.hairspray.dummy_models.PostRepository;
import com.github.wiro34.hairspray.dummy_models.User;
import com.github.wiro34.hairspray.dummy_models.UserRepository;
import javax.inject.Inject;
import static org.testng.AssertJUnit.*;
import org.testng.annotations.Test;

public class HairsprayTest extends AbstractCdiTest {

    @Inject
    private Hairspray factory;

    @Inject
    private UserRepository userRepository;

    @Inject
    private PostRepository postRepository;

    @Test
    public void testBuild() throws Exception {
        int count = userRepository.all().size();
        User user = factory.build(User.class);
        assertNotNull(user);
        assertEquals(userRepository.all().size(), count);
    }

    @Test
    public void testCreate() throws Exception {
        int count = postRepository.all().size();
        Post post = factory.create(Post.class);
        assertNotNull(post);
        assertEquals(postRepository.all().size(), count + 1);
    }
}
