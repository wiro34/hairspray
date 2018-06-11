package com.github.wiro34.hairspray.factory;

import com.github.wiro34.hairspray.ArquillianTest;
import com.github.wiro34.hairspray.dummy_models.Post;
import com.github.wiro34.hairspray.dummy_models.PostFactory;
import com.github.wiro34.hairspray.dummy_models.User;
import com.github.wiro34.hairspray.dummy_models.UserFactory;
import com.github.wiro34.hairspray.factory.FactoryLoader;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.Map;

import static org.testng.AssertJUnit.assertArrayEquals;
import static org.testng.AssertJUnit.assertEquals;

public class FactoryLoaderTest extends ArquillianTest {

    private final FactoryLoader factoryLoader = FactoryLoader.getInstance();

    @Test
    public void testGetFactories() throws Exception {
        Map<Class<?>, Class<?>> factoryMap = factoryLoader.getFactories();
        Object[] models = factoryMap.keySet().stream().sorted(Comparator.comparing(Class::getSimpleName)).toArray();
        Object[] factories = factoryMap.values().stream().sorted(Comparator.comparing(Class::getSimpleName)).toArray();
        assertEquals(factoryMap.size(), 2);
        assertArrayEquals(models, new Object[]{Post.class, User.class});
        assertArrayEquals(factories, new Object[]{PostFactory.class, UserFactory.class});
    }
}
