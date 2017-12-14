package com.github.wiro34.hairspray.factory_loader;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.*;
import javax.inject.Inject;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

@ApplicationScoped
public class FactoryLoadingExtension implements Extension {

    private final List<String> loadedBeans = new ArrayList<>();

    public <t> void processAnnotatedType(@Observes ProcessAnnotatedType<t> event) {
        loadedBeans.add(event.getAnnotatedType().getBaseType().getTypeName());
    }

    public void afterBeanDiscovery(@Observes AfterBeanDiscovery abd, BeanManager bm) {
        // factories to be injectable
        additionalLoadingClasses()
                .stream()
                .map(clazz -> createBean(bm, clazz))
                .forEach(abd::addBean);
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

    private static <T> Bean<T> createBean(BeanManager bm, Class<T> clazz) {
        AnnotatedType<T> at = bm.createAnnotatedType(clazz);
        return bm.createBean(bm.createBeanAttributes(at), clazz, bm.getInjectionTargetFactory(at));
    }
}
