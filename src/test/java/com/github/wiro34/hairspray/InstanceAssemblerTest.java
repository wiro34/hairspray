package com.github.wiro34.hairspray;

import com.github.wiro34.hairspray.dummy_models.User;
import com.github.wiro34.hairspray.dummy_models.User.Sex;
import com.github.wiro34.hairspray.dummy_models.UserFactory;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

public class InstanceAssemblerTest {

    private InstanceAssembler assembler = new InstanceAssembler(User.class);

    private final UserFactory factory = new UserFactory();

    @Test
    public void assembleInstantFields() {
        User user = new User();
        assembler.assembleInstantFields(user, factory);
        assertEquals(user.getId(), null);
        assertEquals(user.getFirstName(), "John");
        assertEquals(user.getLastName(), "Doe");
        assertEquals(user.getFullName(), "John Doe");
        assertEquals(user.getMailAddress(), null);
        assertEquals((int) user.getAge(), 18);
        assertNull(user.getSex());
        assertTrue(user.isActive());
    }

    @Test
    public void assemble() {
        User user = new User();
        user.setFirstName("John");
        assembler.assembleLazyFields(user, factory);
        assertEquals(user.getSex(), Sex.MALE);
    }

    @Test
    public void assemble_updateByLazyFunction() {
        User user = new User();
        user.setFirstName("Jane");
        assembler.assembleLazyFields(user, factory);
        assertEquals(user.getSex(), Sex.FEMALE);
    }

    @Test
    public void assemble_updateWithoutDefaultValue() {
        User user = new User();
        user.setFirstName("Jane");
        user.setSex(Sex.MALE);
        assembler.assembleLazyFields(user, factory);
        assertEquals(user.getFirstName(), "Jane");
        assertEquals(user.getSex(), Sex.FEMALE);
    }
}
