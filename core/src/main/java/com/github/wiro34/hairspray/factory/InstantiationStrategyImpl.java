package com.github.wiro34.hairspray.factory;

public class InstantiationStrategyImpl extends InstantiationStrategy {

    private FactoryLoader factoryLoader;

    private FactoryBuilder factoryBuilder = new FactoryBuilderImpl();

    public InstantiationStrategyImpl(FactoryLoader factoryLoader) {
        this.factoryLoader = factoryLoader;
    }

    @Override
    protected FactoryBuilder getFactoryBuilder() {
        return factoryBuilder;
    }

    @Override
    protected FactoryLoader getFactoryLoader() {
        return factoryLoader;
    }
}
