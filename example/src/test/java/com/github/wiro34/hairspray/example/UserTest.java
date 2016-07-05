package com.github.wiro34.hairspray.example;

import com.github.wiro34.hairspray.Hairspray;
import javax.inject.Inject;
import static org.testng.AssertJUnit.assertEquals;
import org.testng.annotations.Test;

public class UserTest extends AbstractCdiTest {

    @Inject
    private Hairspray factory;

    @Test
    public void testGetFullName() throws Exception {
        User user = factory.build(User.class, (u) -> {
                              u.setFirstName("Foo");
                              u.setLastName("Bar");
                          });
        assertEquals(user.getFullName(), "Foo Bar");
    }
}
