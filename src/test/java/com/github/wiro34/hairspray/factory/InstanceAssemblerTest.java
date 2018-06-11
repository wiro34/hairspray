package com.github.wiro34.hairspray.factory;

import com.github.wiro34.hairspray.dummy_models.User;
import com.github.wiro34.hairspray.dummy_models.User.Sex;
import com.github.wiro34.hairspray.dummy_models.UserFactory;
import org.testng.annotations.Test;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

import static org.testng.Assert.*;

public class InstanceAssemblerTest {

    private InstanceAssembler assembler = new InstanceAssembler(User.class, new UserFactory());

    @Test
    public void assembleInstantFields() {
        User user = new User();
        assembler.assembleInstantFields(user);
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
    public void assembleDynamicFields() {
        User user = new User();
        user.setFirstName("John");
        assembler.assembleDynamicFields(user);
        Date date = Date.from(ZonedDateTime.of(2112, 1, 2, 3, 4, 56, 0, ZoneId.systemDefault()).toInstant());
        assertEquals(user.getCreatedAt(), new Timestamp(date.getTime()));
    }

    @Test
    public void assembleLazyFields() {
        User user = new User();
        user.setFirstName("John");
        assembler.assembleLazyFields(user);
        assertEquals(user.getSex(), Sex.MALE);
    }

    @Test
    public void assembleLazyFields_updateByLazyFunction() {
        User user = new User();
        user.setFirstName("Jane");
        assembler.assembleLazyFields(user);
        assertEquals(user.getSex(), Sex.FEMALE);
    }

    @Test
    public void assembleLazyFields_updateWithoutDefaultValue() {
        User user = new User();
        user.setFirstName("Jane");
        user.setSex(Sex.MALE);
        assembler.assembleLazyFields(user);
        assertEquals(user.getFirstName(), "Jane");
        assertEquals(user.getSex(), Sex.FEMALE);
    }
}
