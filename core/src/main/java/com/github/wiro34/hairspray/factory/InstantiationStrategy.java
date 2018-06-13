package com.github.wiro34.hairspray.factory;

import com.github.wiro34.hairspray.exception.RuntimeInstantiationException;

import java.util.function.BiConsumer;

public abstract class InstantiationStrategy {

    protected abstract FactoryBuilder getFactoryBuilder();

    protected abstract FactoryLoader getFactoryLoader();

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
        return getFactoryLoader().getFactoryFor(clazz)
                .map(factoryClass -> getFactoryBuilder().getFactoryInstance(factoryClass))
                .orElseThrow(() -> new RuntimeInstantiationException("Factory is not defined: class=" + clazz.getSimpleName()));
    }
}
