package com.github.wiro34.hairspray.factory.strategy;

import com.github.wiro34.hairspray.exception.RuntimeInstantiationException;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Dependent
public class CreateStrategy extends InstantiationStrategy {

    @Inject
    private EntityManager entityManager;

    @Override
    public <T> void beforeAssemble(T instance) {
        super.beforeAssemble(instance);

        if (entityManager == null) {
            throw new UnsupportedOperationException("EntityManager is not provided.");
        }
    }

    @Override
    public <T> void afterAssemble(T instance) {
        super.afterAssemble(instance);

        try {
            entityManager.persist(instance);
        } catch (Exception e) {
            throw new RuntimeInstantiationException(e);
        }
    }

}
