package com.github.wiro34.hairspray.jpa.factory;

import com.github.wiro34.hairspray.exception.RuntimeInstantiationException;
import com.github.wiro34.hairspray.factory.FactoryBuilder;
import com.github.wiro34.hairspray.factory.FactoryLoader;
import com.github.wiro34.hairspray.factory.InstanceAssembler;
import com.github.wiro34.hairspray.factory.InstantiationStrategy;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Dependent
public class CreateStrategy extends InstantiationStrategy {

    @Inject
    private EntityManager entityManager;

    @Inject
    private FactoryBuilder factoryBuilder;

    @Inject
    private FactoryLoader factoryLoader;

    @Override
    protected FactoryLoader getFactoryLoader() {
        return factoryLoader;
    }

    @Override
    protected FactoryBuilder getFactoryBuilder() {
        return factoryBuilder;
    }

    @Override
    public <T> void afterAssemble(T instance, InstanceAssembler assembler) {
        super.afterAssemble(instance, assembler);
        try {
            entityManager.persist(instance);
        } catch (Exception e) {
            throw new RuntimeInstantiationException(e);
        }
    }

}
