package com.github.wiro34.hairspray.factory_loader;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;

public class FactoryLoadingExtension implements Extension {

    public void beforeBeanDiscovery(@Observes BeforeBeanDiscovery event, BeanManager bm) {
        FactoryLoader factoryLoader = FactoryLoader.getInstance();
        factoryLoader.getFactories().values().stream().forEach((factoryClass) -> {
            event.addAnnotatedType(bm.createAnnotatedType(factoryClass));
        });
    }
}
