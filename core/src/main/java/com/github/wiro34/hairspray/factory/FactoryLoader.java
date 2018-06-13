package com.github.wiro34.hairspray.factory;

import com.github.wiro34.hairspray.annotation.Factory;
import com.github.wiro34.hairspray.misc.Memorized;
import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@link Factory} アノテーションの付与されたファクトリクラスを読み込むためのクラス
 */
public class FactoryLoader {

    private static final Logger LOGGER = Logger.getLogger(FactoryLoader.class.getName());

    private final Memorized<Map<Class<?>, Class<?>>> memorizedFactories = new Memorized<>(this::loadFactories);

    public Map<Class<?>, Class<?>> getFactories() {
        return memorizedFactories.get();
    }

    public Optional<Class<?>> getFactoryFor(Class<?> clazz) {
        return Optional
                .ofNullable(getFactories().get(clazz));
    }

    private Map<Class<?>, Class<?>> loadFactories() {
        return getFactoryClasses().stream().reduce(new HashMap<Class<?>, Class<?>>(), (map, cls) -> {
            Class<?> modelClass = cls.getDeclaredAnnotation(Factory.class).value();
            if (map.containsKey(modelClass)) {
                throw new IllegalStateException("Factory definition for " + modelClass.getSimpleName() + " class is duplicated.");
            }
            map.put(modelClass, cls);
            return map;
        }, (map1, map2) -> map1);
    }

    private Set<Class<?>> getFactoryClasses() {
        final Reflections reflections = new Reflections("", new TypeAnnotationsScanner().filterResultsBy((name) -> Factory.class.getName().equals(name)));
        Set<Class<?>> factoryClasses = reflections.getTypesAnnotatedWith(Factory.class, true);
        LOGGER.log(Level.INFO, "{0} factories found", factoryClasses.size());
        return factoryClasses;
    }
}
