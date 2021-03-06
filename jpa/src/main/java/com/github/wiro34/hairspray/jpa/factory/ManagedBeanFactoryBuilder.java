package com.github.wiro34.hairspray.jpa.factory;

import com.github.wiro34.hairspray.exception.RuntimeInstantiationException;
import com.github.wiro34.hairspray.factory.FactoryBuilder;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import java.util.Optional;
import java.util.Set;

@Dependent
public class ManagedBeanFactoryBuilder implements FactoryBuilder {
    @Inject
    private BeanManager beanManager;

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getFactoryInstance(Class<T> factoryClass) {
        Set<Bean<?>> beans = beanManager.getBeans(factoryClass);
        return Optional.ofNullable((Bean<T>) beanManager.resolve(beans)).map(bean -> {
            CreationalContext<T> cc = beanManager.createCreationalContext(bean);
            return bean.create(cc);
        }).orElseThrow(() -> new RuntimeInstantiationException("Failed to instantiate factory: " + factoryClass.getSimpleName()));
    }
}
