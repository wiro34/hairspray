package com.github.wiro34.hairspray.factory;

import com.github.wiro34.hairspray.dummy_models.UserFactory;
import com.github.wiro34.hairspray.exception.RuntimeInstantiationException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertNotNull;

public class FactoryBuilderImplTest {

    private final FactoryBuilderImpl builder = new FactoryBuilderImpl();

    private UserFactory factory;

    @BeforeMethod
    public void setUp() {
        factory = builder.getFactoryInstance(UserFactory.class);
    }

    @Test
    public void testGetFactoryInstance() throws Exception {
        assertNotNull(factory);
    }

    @Test(expectedExceptions = RuntimeInstantiationException.class)
    public void testGetFactoryInstanceWhenFactoryClassHasNoDefaultConstractor() {
        builder.getFactoryInstance(InvalidFormFactory.class);
    }

    public static class InvalidFormFactory {
        public InvalidFormFactory(String foo) {
        }
    }
}
