package com.github.wiro34.hairspray.factory;

import com.github.wiro34.hairspray.annotation.Factory;
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

    private static Map<Class<?>, Class<?>> factories;

    public Optional<Class<?>> getFactoryFor(Class<?> clazz) {
        return Optional
                .ofNullable(loadFactories(clazz).get(clazz));
    }

    public Map<Class<?>, Class<?>> getFactories() {
        return loadFactories(null);
    }

    private Map<Class<?>, Class<?>> loadFactories(Class<?> clazz) {
        if (factories == null) {
            factories = getFactoryClasses(clazz).stream().reduce(new HashMap<Class<?>, Class<?>>(), (map, cls) -> {
                Class<?> modelClass = cls.getDeclaredAnnotation(Factory.class).value();
                if (map.containsKey(modelClass)) {
                    throw new IllegalStateException("Factory definition for " + modelClass.getSimpleName() + " class is duplicated.");
                }
                map.put(modelClass, cls);
                return map;
            }, (map1, map2) -> map1);
        }
        return factories;
    }

    /**
     * {@link Factory} アノテーションが付与されたファクトリクラスを取得します。
     */
    private Set<Class<?>> getFactoryClasses(Class<?> clazz) {
        // テストクラスとファクトリクラスが異なる jar に分割されてるようなケースの場合、
        // Reflections の prefix にパッケージを明示する必要がある
        // パフォーマンスは悪くなるが、モデルクラスとテストクラスは少なくともトップレベルパッケージは同一であると推定して prefix を設定
        final Reflections reflections = new Reflections(
                getTopLevelPackage(clazz),
                new TypeAnnotationsScanner().filterResultsBy((name) -> Factory.class.getName().equals(name))
        );
        Set<Class<?>> factoryClasses = reflections.getTypesAnnotatedWith(Factory.class, true);
        LOGGER.log(Level.INFO, "{0} factories found!", factoryClasses.size());
        return factoryClasses;
    }

    private String getTopLevelPackage(Class<?> clazz) {
        return Optional.ofNullable(clazz)
                .map(c -> c.getPackage().getName().split("\\.")[0])
                .orElse("");
    }
}
