package com.github.wiro34.hairspray.factory_loader;

import com.github.wiro34.hairspray.exception.RuntimeInstantiationException;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import java.util.Optional;
import java.util.Set;

@ApplicationScoped
public class ManagedBeanFactoryProvider implements FactoryProvider {
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
