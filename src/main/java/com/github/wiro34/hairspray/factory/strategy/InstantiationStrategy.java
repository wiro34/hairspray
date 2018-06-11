package com.github.wiro34.hairspray.factory.strategy;

import com.github.wiro34.hairspray.exception.RuntimeInstantiationException;
import com.github.wiro34.hairspray.factory.FactoryLoader;
import com.github.wiro34.hairspray.factory.InstanceAssembler;
import com.github.wiro34.hairspray.factory.builder.FactoryBuilder;
import com.github.wiro34.hairspray.factory.builder.FactoryBuilderProvider;
import com.github.wiro34.hairspray.misc.Memorized;

import javax.inject.Inject;
import java.util.function.BiConsumer;

public abstract class InstantiationStrategy {

    private static final FactoryLoader factoryLoader = FactoryLoader.getInstance();

    @Inject
    private FactoryBuilderProvider factoryBuilderProvider = new FactoryBuilderProvider();

    private Memorized<FactoryBuilder> factoryBuilder = new Memorized<>(() -> factoryBuilderProvider.getBuilder());

    public <T> T getInstance(Class<T> clazz, BiConsumer<T, Integer> initializer, int index) {
        try {
            final T instance = clazz.newInstance();
            final Object factory = getFactoryFor(clazz);
            final InstanceAssembler assembler = new InstanceAssembler(clazz, factory);
            this.beforeAssemble(instance, assembler);
            assembler.assembleInstantFields(instance);
            assembler.assembleDynamicFields(instance);
            if (initializer != null) {
                initializer.accept(instance, index);
            }
            this.afterAssemble(instance, assembler);
            assembler.assembleLazyFields(instance);
            return instance;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeInstantiationException(e);
        }
    }

    protected <T> void beforeAssemble(T instance, InstanceAssembler assembler) {
    }

    protected <T> void afterAssemble(T instance, InstanceAssembler assembler) {
    }

    private Object getFactoryFor(Class<?> clazz) {
        return factoryLoader.getFactoryFor(clazz)
                .map(factoryClass -> factoryBuilder.get().getFactoryInstance(factoryClass))
                .orElseThrow(() -> new RuntimeInstantiationException("Factory is not defined: class=" + clazz.getSimpleName()));
    }
}
