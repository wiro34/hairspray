package com.github.wiro34.hairspray.factory_loader;

import com.github.wiro34.hairspray.annotation.Factory;
import com.github.wiro34.hairspray.misc.Memorizer;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;

public class FactoryLoader {

    private static final Logger LOGGER = Logger.getLogger(FactoryLoader.class.getName());

    private static final FactoryLoader INSTANCE = new FactoryLoader();

    private final Memorizer<Map<Class<?>, Class<?>>> memorizedFactories = new Memorizer<>(() -> loadFactories());

    public static FactoryLoader getInstance() {
        return INSTANCE;
    }

    private FactoryLoader() {
    }

    public Map<Class<?>, Class<?>> getFactories() {
        return memorizedFactories.get();
    }

    private Map<Class<?>, Class<?>> loadFactories() {
        return getFactoryClasses().stream().reduce(new HashMap<Class<?>, Class<?>>(), (map, cls) -> {
            Class<?> modelClass = cls.getDeclaredAnnotation(Factory.class).value();
            if (map.containsKey(modelClass)) {
                throw new IllegalStateException("Definition for the class " + modelClass.getSimpleName() + " is duplicated.");
            } else if (modelClass == null) {
                throw new NoSuchElementException("No value present");
            }
            map.put(modelClass, cls);
            return map;
        }, (map1, map2) -> map1);
    }

    private Set<Class<?>> getFactoryClasses() {
        final Reflections reflections = new Reflections("", new TypeAnnotationsScanner().filterResultsBy((name) -> name.equals(Factory.class.getName())));
        Set<Class<?>> factoryClasses = reflections.getTypesAnnotatedWith(Factory.class, true);
        LOGGER.log(Level.INFO, "{0} factories found", factoryClasses.size());
        return factoryClasses;
    }
}
