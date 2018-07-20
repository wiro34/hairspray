package com.github.wiro34.hairspray.jpa.extension;

import com.github.wiro34.hairspray.factory.FactoryLoader;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class FactoryLoaderProducer {

    private static FactoryLoader factoryLoader = null;

    @Produces
    public FactoryLoader produceFactoryLoader() {
        return getFactoryLoader();
    }

    public static FactoryLoader getFactoryLoader() {
        if (factoryLoader == null) {
            factoryLoader = new FactoryLoader();
        }
        return factoryLoader;
    }
}
