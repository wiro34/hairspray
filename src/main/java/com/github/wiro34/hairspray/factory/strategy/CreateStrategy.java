package com.github.wiro34.hairspray.factory.strategy;

import com.github.wiro34.hairspray.exception.RuntimeInstantiationException;
import com.github.wiro34.hairspray.factory.InstanceAssembler;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Dependent
public class CreateStrategy extends InstantiationStrategy {

    @Inject
    private EntityManager entityManager;

    @Override
    public <T> void beforeAssemble(T instance, InstanceAssembler assembler) {
        super.beforeAssemble(instance, assembler);

        if (entityManager == null) {
            throw new UnsupportedOperationException("EntityManager is not provided.");
        }
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
