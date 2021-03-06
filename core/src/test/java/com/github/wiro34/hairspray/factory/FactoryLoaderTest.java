package com.github.wiro34.hairspray.factory;

import com.github.wiro34.hairspray.dummy_models.Post;
import com.github.wiro34.hairspray.dummy_models.PostFactory;
import com.github.wiro34.hairspray.dummy_models.User;
import com.github.wiro34.hairspray.dummy_models.UserFactory;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.Map;

import static org.testng.AssertJUnit.assertArrayEquals;
import static org.testng.AssertJUnit.assertEquals;

public class FactoryLoaderTest {

    private final FactoryLoader factoryLoader = new FactoryLoader();

    @Test
    public void testGetFactories() {
        Map<Class<?>, Class<?>> factoryMap = factoryLoader.getFactories();
        Object[] models = factoryMap.keySet().stream().sorted(Comparator.comparing(Class::getSimpleName)).toArray();
        Object[] factories = factoryMap.values().stream().sorted(Comparator.comparing(Class::getSimpleName)).toArray();
        assertEquals(factoryMap.size(), 2);
        assertArrayEquals(models, new Object[]{Post.class, User.class});
        assertArrayEquals(factories, new Object[]{PostFactory.class, UserFactory.class});
    }
}
