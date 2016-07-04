package com.github.wiro34.hairspray;

import com.github.wiro34.hairspray.dummy_models.User;
import com.github.wiro34.hairspray.dummy_models.User.Sex;
import com.github.wiro34.hairspray.dummy_models.UserFactory;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNull;

public class InstanceAssemblerTest {

    private final UserFactory factory = new UserFactory();

    @Test
    public void testAssemble() {
        User user = new User();
        assertNull(user.getId());
        assertNull(user.getName());
        assertNull(user.getMailAddress());
        assertNull(user.getAge());
        assertNull(user.getSex());
        InstanceAssembler.assemble(user, factory);
        assertEquals(user.getId(), null);
        assertEquals(user.getName(), "John Doe (18)");
        assertEquals(user.getMailAddress(), null);
        assertEquals((int) user.getAge(), 18);
        assertEquals(user.getSex(), Sex.MALE);
    }

    @Test
    public void testAssembleWithMultipleObjects() {
        User user1 = new User();
        User user2 = new User();
        InstanceAssembler.assemble(user1, factory);
        InstanceAssembler.assemble(user2, factory);
        assertEquals((int) user1.getAge(), 18);
        assertEquals((int) user2.getAge(), 18);
    }
}
