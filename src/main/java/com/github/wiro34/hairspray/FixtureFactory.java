package com.github.wiro34.hairspray;

import com.github.wiro34.hairspray.annotation.Factory;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public abstract class FixtureFactory {

    protected abstract <T> T build(Class<T> clazz, BiConsumer<T, Integer> initializer, int index);

    protected abstract <T> T create(Class<T> clazz, BiConsumer<T, Integer> initializer, int index);

}
