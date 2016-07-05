package com.github.wiro34.hairspray.factory_loader;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.inject.Inject;

public class FactoryLoadingExtension implements Extension {

    private final List<String> loadedBeans = new ArrayList<>();

    public <t> void processAnnotatedType(@Observes ProcessAnnotatedType<t> event) {
        loadedBeans.add(event.getAnnotatedType().getBaseType().getTypeName());
    }

    public void afterBeanDiscovery(@Observes AfterBeanDiscovery event, BeanManager bm) {
        additionalLoadingClasses().forEach(clazz -> {
            // factories to be injectable
            event.addBean(createBean(bm, clazz));
        });
    }

    private List<Class<?>> additionalLoadingClasses() {
        return FactoryLoader.getInstance().getFactories().values().stream()
                .reduce(new ArrayList<>(),
                        (classes, factoryClass) -> {
                            classes.add(factoryClass);
                            classes.addAll(injectableFields(factoryClass));
                            return classes;
                        }, (BinaryOperator<List<Class<?>>>) (left, right) -> {
                            left.addAll(right);
                            return left;
                        })
                .stream()
                .filter(clazz -> !loadedBeans.contains(clazz.getName()))
                .collect(Collectors.toList());

    }

    private List<Class<?>> injectableFields(Class<?> factoryClass) {
        return Arrays.stream(factoryClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Inject.class))
                .map(Field::getType)
                .collect(Collectors.toList());
    }

    private Bean<?> createBean(BeanManager bm, Class<?> clazz) {
        AnnotatedType at = bm.createAnnotatedType(clazz);
        final InjectionTarget it = bm.createInjectionTarget(at);
        return bm.createBean(bm.createBeanAttributes(at), clazz, bm.getInjectionTargetFactory(at));
    }
}
