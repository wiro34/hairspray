package com.github.wiro34.hairspray.jpa.factory;

import com.github.wiro34.hairspray.exception.RuntimeInstantiationException;
import com.github.wiro34.hairspray.jpa.ArquillianTest;
import com.github.wiro34.hairspray.jpa.dummy_models.User;
import com.github.wiro34.hairspray.jpa.dummy_models.UserFactory;
import org.testng.annotations.Test;

import javax.inject.Inject;

import static org.testng.AssertJUnit.assertNotNull;

public class ManagedBeanFactoryBuilderTest extends ArquillianTest {

    @Inject
    private ManagedBeanFactoryBuilder builder;

    @Test
    public void testGetFactoryInstance() throws Exception {
        UserFactory factory = builder.getFactoryInstance(UserFactory.class);
        User user = new User();
        assertNotNull(factory);
    }

    @Test(expectedExceptions = RuntimeInstantiationException.class)
    public void testGetFactoryInstanceWhenFactoryIsUnregistered() throws Exception {
        builder.getFactoryInstance(UnregisteredFactory.class);
    }

    private static class UnregisteredFactory {

    }
}
