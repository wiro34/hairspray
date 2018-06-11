package com.github.wiro34.hairspray.factory.builder;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class FactoryBuilderProvider {

    @Inject
    private ManagedBeanFactoryBuilder managedBeanFactoryBuilder;

    private FactoryBuilder builder = new PojoFactoryBuilder();

    public FactoryBuilder getBuilder() {
        return builder;
    }

    @PostConstruct
    public void init() {
        builder = managedBeanFactoryBuilder;
    }
}
