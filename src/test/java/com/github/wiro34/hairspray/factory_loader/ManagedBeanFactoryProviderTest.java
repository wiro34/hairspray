package com.github.wiro34.hairspray.factory_loader;

import com.github.wiro34.hairspray.AbstractCdiTest;
import com.github.wiro34.hairspray.Hairspray;
import com.github.wiro34.hairspray.dummy_models.User;
import com.github.wiro34.hairspray.dummy_models.UserFactory;
import com.github.wiro34.hairspray.exception.RuntimeInstantiationException;
import javax.inject.Inject;
import static org.testng.AssertJUnit.*;
import org.testng.annotations.Test;

public class ManagedBeanFactoryProviderTest extends AbstractCdiTest {
    @Inject
    private Hairspray factorsy;
    
    @Inject
    private ManagedBeanFactoryProvider factoryLoader;

    @Test
    public void testGetFactoryInstance() throws Exception {
        UserFactory factory = factoryLoader.getFactoryInstance(UserFactory.class);
        User user = new User();
        assertNotNull(factory);
    }

    @Test(expectedExceptions = RuntimeInstantiationException.class)
    public void testGetFactoryInstanceWhenFactoryIsUnregistered() throws Exception {
        factoryLoader.getFactoryInstance(UnregisteredFactory.class);
    }

    public static class UnregisteredFactory {
    }
}
