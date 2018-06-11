package com.github.wiro34.hairspray.factory.builder;

import com.github.wiro34.hairspray.exception.RuntimeInstantiationException;

public class PojoFactoryBuilder implements FactoryBuilder{

    @Override
    public <T> T getFactoryInstance(Class<T> factoryClass) {
        try {
            return factoryClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeInstantiationException(e);
        }
    }
}
