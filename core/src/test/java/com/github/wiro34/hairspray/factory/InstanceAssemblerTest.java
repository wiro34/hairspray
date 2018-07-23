package com.github.wiro34.hairspray.factory;

import com.github.wiro34.hairspray.dummy_models.User;
import com.github.wiro34.hairspray.dummy_models.User.Sex;
import com.github.wiro34.hairspray.dummy_models.UserFactory;
import lombok.Data;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.LocalDateTime;

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
        assertEquals(user.getCreatedAt(), LocalDateTime.of(2112, 1, 2, 3, 4, 56, 0));
    }

    @DataProvider
    public Object[][] provideDataForLazyFields() {
        return new Object[][]{
                {"John", Sex.MALE},
                {"Jane", Sex.FEMALE}
        };
    }

    @Test(dataProvider = "provideDataForLazyFields")
    public void assembleLazyFields(String firstName, Sex sex) {
        User user = new User();
        user.setFirstName(firstName);
        assembler.assembleLazyFields(user);
        assertEquals(user.getSex(), sex);
    }

    @Test
    public void assembleLazyFields_whenValueAlreadySet() {
        User user = new User();
        user.setFirstName("Jane");
        user.setSex(Sex.MALE);
        assembler.assembleLazyFields(user);
        assertEquals(user.getSex(), Sex.MALE);
    }
}
