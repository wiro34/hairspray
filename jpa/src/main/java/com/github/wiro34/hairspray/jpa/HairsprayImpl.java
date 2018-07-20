package com.github.wiro34.hairspray.jpa;

import com.github.wiro34.hairspray.Hairspray;
import com.github.wiro34.hairspray.factory.InstantiationStrategy;
import com.github.wiro34.hairspray.jpa.factory.BuildStrategy;
import com.github.wiro34.hairspray.jpa.factory.CreateStrategy;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.function.BiConsumer;

@RequestScoped
public class HairsprayImpl extends Hairspray {

    @Inject
    private BuildStrategy buildStrategy;

    @Inject
    private CreateStrategy createStrategy;

    @Override
    protected <T> T assemble(Class<T> clazz, InstantiationStrategy strategy, BiConsumer<T, Integer> initializer, int index) {
        return strategy.getInstance(clazz, initializer, index);
    }

    @Override
    protected <T> T build(Class<T> clazz, BiConsumer<T, Integer> initializer, int index) {
        return assemble(clazz, buildStrategy, initializer, index);
    }

    @Override
    protected <T> T create(Class<T> clazz, BiConsumer<T, Integer> initializer, int index) {
        return assemble(clazz, createStrategy, initializer, index);
    }
}
