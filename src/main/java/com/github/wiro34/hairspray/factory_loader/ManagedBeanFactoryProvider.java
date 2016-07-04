package com.github.wiro34.hairspray.factory_loader;

import com.github.wiro34.hairspray.exception.RuntimeInstantiationException;
import java.util.Optional;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ApplicationScoped
public class ManagedBeanFactoryProvider implements FactoryProvider {
    @Inject
    private FactoryLoadingExtension extension;  // NOTE: don't remove this

    @Inject
    private BeanManager beanManager;

    @Override
    public <T> T getFactoryInstance(Class<T> factoryClass) {
        Set<Bean<?>> beans = beanManager.getBeans(factoryClass);
        return Optional.ofNullable((Bean<T>) beanManager.resolve(beans)).map(bean -> {
            CreationalContext<T> cc = beanManager.createCreationalContext(bean);
            return bean.create(cc);
        }).orElseThrow(() -> new RuntimeInstantiationException("Failed to instantiate factory: " + factoryClass.getSimpleName()));
    }
}
