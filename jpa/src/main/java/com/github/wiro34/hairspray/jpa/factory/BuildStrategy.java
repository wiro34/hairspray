package com.github.wiro34.hairspray.jpa.factory;

import com.github.wiro34.hairspray.factory.FactoryBuilder;
import com.github.wiro34.hairspray.factory.FactoryBuilderImpl;
import com.github.wiro34.hairspray.factory.FactoryLoader;
import com.github.wiro34.hairspray.factory.InstantiationStrategy;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class BuildStrategy extends InstantiationStrategy {

    private FactoryBuilder factoryBuilder = new FactoryBuilderImpl();

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

}
