package com.github.wiro34.hairspray;

import com.github.wiro34.hairspray.dummy_models.User;
import com.github.wiro34.hairspray.dummy_models.User.Sex;
import com.github.wiro34.hairspray.dummy_models.UserFactory;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNull;
import org.testng.annotations.Test;

public class InstanceAssemblerTest {

    private final UserFactory factory = new UserFactory();

    @Test
    public void testAssemble() {
        User user = new User();
        assertNull(user.getId());
        assertNull(user.getFirstName());
        assertNull(user.getLastName());
        assertNull(user.getMailAddress());
        assertNull(user.getAge());
        assertNull(user.getSex());
        new InstanceAssembler(User.class).assemble(user, factory);
        assertEquals(user.getId(), null);
        assertEquals(user.getFirstName(), "John");
        assertEquals(user.getLastName(), "Doe");
        assertEquals(user.getFullName(), "John Doe");
        assertEquals(user.getMailAddress(), null);
        assertEquals((int) user.getAge(), 18);
        assertEquals(user.getSex(), Sex.MALE);
    }

    @Test
    public void testAssembleWhenAnyFieldIsSet() {
        User user = new User();
        user.setFirstName("Jane");
        new InstanceAssembler(User.class).assemble(user, factory);
        assertEquals(user.getFirstName(), "Jane");
        assertEquals(user.getSex(), Sex.FEMALE);
    }
}
