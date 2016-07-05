package com.github.wiro34.hairspray.example;

import com.github.wiro34.hairspray.Hairspray;
import java.util.List;
import javax.inject.Inject;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class PostRepositoryTest extends AbstractCdiTest {

    @Inject
    private Hairspray factory;

    @Inject
    private PostRepository postRepository;

    private List<Post> posts;

    @BeforeMethod
    public void setUp() {
        posts = factory.createList(Post.class, 3);
        posts.get(0).setSubject("Test Post #1");
        posts.get(1).setSubject("Test Post #2");
        posts.get(2).setSubject("Test Post #3");
        posts.get(2).setAuther(posts.get(0).getAuther());
        postRepository.save(posts.get(2));
    }

    @Test
    public void testFind() {
        assertEquals(postRepository.find(posts.get(1).getId()).get().getSubject(), "Test Post #2");
        assertFalse(postRepository.find(posts.get(2).getId() + 1).isPresent());
    }

    @Test
    public void testFindBy() {
        User user = posts.get(0).getAuther();
        List<Post> founds = postRepository.findBy(user);
        assertEquals(founds.size(), 2);
        founds.stream().forEach((p) -> {
            assertEquals(p.getAuther(), user);
        });
    }

    @Test
    public void testAll() {
        assertEquals(postRepository.all(), posts);
    }

    @Test
    public void testSave() {
        Post post = posts.get(2);
        assertEquals(postRepository.find(post.getId()).get().getSubject(), "Test Post #3");
        post.setSubject("Foo");
        postRepository.save(post);
        assertEquals(postRepository.find(post.getId()).get().getSubject(), "Foo");
    }

    @Test
    public void testFirst() {
        assertEquals(postRepository.first().get(), posts.get(0));
    }

    @Test
    public void testLast() {
        assertEquals(postRepository.last().get(), posts.get(2));
    }
}
