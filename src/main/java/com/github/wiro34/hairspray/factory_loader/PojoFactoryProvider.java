package com.github.wiro34.hairspray.factory_loader;

import com.github.wiro34.hairspray.exception.RuntimeInstantiationException;

public class PojoFactoryProvider implements FactoryProvider {

    @Override
    public <T> T getFactoryInstance(Class<T> factoryClass) {
        try {
            return factoryClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeInstantiationException(e);
        }
    }
}
