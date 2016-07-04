package com.github.wiro34.hairspray;

import static com.github.wiro34.hairspray.InstanceAssembler.assemble;
import com.github.wiro34.hairspray.exception.RuntimeInstantiationException;
import com.github.wiro34.hairspray.factory_loader.ManagedBeanFactoryProvider;
import com.github.wiro34.hairspray.factory_loader.FactoryProvider;
import com.github.wiro34.hairspray.factory_loader.PojoFactoryProvider;
import java.util.function.Consumer;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Named
@ApplicationScoped
public class Hairspray implements FixtureFactory {

    private static final Logger LOGGER = Logger.getLogger(Hairspray.class.getName());

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
    public <T> T build(Class<T> clazz, Consumer<T> initializer) {
        try {
            final T instance = clazz.newInstance();
            return factoryProvider.findFactoryFor(clazz)
                    .map(f -> {
                        T assembled = assemble(instance, f);
                        initializer.accept(assembled);
                        return assembled;
                    })
                    .orElseThrow(() -> new RuntimeInstantiationException("Factory of " + clazz.getSimpleName() + " is not found"));
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeInstantiationException(e);
        }
    }

    @Override
    @Transactional
    public <T> T create(Class<T> clazz, Consumer<T> initializer) {
        final T instance = build(clazz, initializer);
        try {
            entityManager.persist(instance);
        } catch (Exception e) {
            throw new RuntimeInstantiationException(e);
        }
        return instance;
    }
}
