package com.github.wiro34.hairspray.factory_loader;

import java.util.Optional;

public interface FactoryProvider {

    public <T> T getFactoryInstance(Class<T> factoryClass);

    public default Optional<Object> findFactoryFor(Class<?> clazz) {
        return Optional
                .ofNullable(FactoryLoader.getInstance().getFactories().get(clazz))
                .map(factoryClass -> getFactoryInstance(factoryClass));
    }
}
