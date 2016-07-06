package com.github.wiro34.hairspray;

import com.github.wiro34.hairspray.exception.RuntimeInstantiationException;
import com.github.wiro34.hairspray.factory_loader.ManagedBeanFactoryProvider;
import com.github.wiro34.hairspray.factory_loader.FactoryProvider;
import com.github.wiro34.hairspray.factory_loader.PojoFactoryProvider;
import java.util.function.BiConsumer;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Named
@ApplicationScoped
public class Hairspray extends FixtureFactory {

    @Inject
    private EntityManager entityManager;

    @Inject
    private ManagedBeanFactoryProvider managedBeanFactoryProvider;

    private FactoryProvider factoryProvider = new PojoFactoryProvider();

    @PostConstruct
    private void search() {
        factoryProvider = managedBeanFactoryProvider;
    }

    @Override
    public <T> T build(Class<T> clazz, BiConsumer<T, Integer> initializer, int index) {
        try {
            final InstanceAssembler assembler = new InstanceAssembler(clazz);
            final T instance = clazz.newInstance();
            return factoryProvider.findFactoryFor(clazz)
                    .map(f -> {
                        initializer.accept(instance, index);
                        assembler.assemble(instance, f);
                        return instance;
                    })
                    .orElseThrow(() -> new RuntimeInstantiationException("Factory of " + clazz.getSimpleName() + " is not found"));
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeInstantiationException(e);
        }
    }

    @Override
    @Transactional
    public <T> T create(Class<T> clazz, BiConsumer<T, Integer> initializer, int index) {
        final T instance = build(clazz, initializer, index);
        try {
            entityManager.persist(instance);
        } catch (Exception e) {
            throw new RuntimeInstantiationException(e);
        }
        return instance;
    }
}
