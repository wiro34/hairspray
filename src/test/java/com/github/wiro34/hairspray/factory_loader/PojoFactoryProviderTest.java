package com.github.wiro34.hairspray.factory_loader;

import com.github.wiro34.hairspray.dummy_models.UserFactory;
import com.github.wiro34.hairspray.exception.RuntimeInstantiationException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.*;

public class PojoFactoryProviderTest {

    private final PojoFactoryProvider factoryLoader = new PojoFactoryProvider();

    private UserFactory factory;

    @BeforeMethod
    public void setUp() {
        factory = factoryLoader.getFactoryInstance(UserFactory.class);
    }

    @Test
    public void testGetFactoryInstance() throws Exception {
        assertNotNull(factory);
        assertEquals((int) factory.age(null), 18);
    }

    @Test(expectedExceptions = RuntimeInstantiationException.class)
    public void testGetFactoryInstanceWhenFactoryClassHasNoDefaultConstractor() {
        factoryLoader.getFactoryInstance(InvalidFormFactory.class);
    }

    public static class InvalidFormFactory {
        public InvalidFormFactory(String foo) {
        }
    }
}
