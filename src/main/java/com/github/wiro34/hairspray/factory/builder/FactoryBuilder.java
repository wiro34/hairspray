package com.github.wiro34.hairspray.factory.builder;

public interface FactoryBuilder {

    <T> T getFactoryInstance(Class<T> factoryClass);

}
