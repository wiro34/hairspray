package com.github.wiro34.hairspray.factory_loader;

import com.github.wiro34.hairspray.*;
import com.github.wiro34.hairspray.dummy_models.Post;
import com.github.wiro34.hairspray.dummy_models.PostFactory;
import com.github.wiro34.hairspray.dummy_models.User;
import com.github.wiro34.hairspray.dummy_models.UserFactory;
import java.util.Map;
import static org.testng.AssertJUnit.*;
import org.testng.annotations.Test;

public class FactoryLoaderTest extends AbstractCdiTest {

    private final FactoryLoader factoryLoader = FactoryLoader.getInstance();

    @Test
    public void testGetFactories() throws Exception {
        Map<Class<?>, Class<?>> factoryMap = factoryLoader.getFactories();
        Object[] modeles = factoryMap.keySet().stream().sorted((a, b) -> a.getSimpleName().compareTo(b.getSimpleName())).toArray();
        Object[] factories = factoryMap.values().stream().sorted((a, b) -> a.getSimpleName().compareTo(b.getSimpleName())).toArray();
        assertEquals(factoryMap.size(), 2);
        assertArrayEquals(modeles, new Object[]{Post.class, User.class});
        assertArrayEquals(factories, new Object[]{PostFactory.class, UserFactory.class});
    }
}
