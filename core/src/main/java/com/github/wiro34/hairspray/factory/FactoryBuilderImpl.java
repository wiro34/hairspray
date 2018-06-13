package com.github.wiro34.hairspray.factory;

import com.github.wiro34.hairspray.exception.RuntimeInstantiationException;

/**
 * {@link FactoryBuilder} の POJO 実装
 */
public class FactoryBuilderImpl implements FactoryBuilder {

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T getFactoryInstance(Class<T> factoryClass) {
        try {
            return factoryClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeInstantiationException(e);
        }
    }
}
