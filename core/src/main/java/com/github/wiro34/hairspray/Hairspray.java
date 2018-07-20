package com.github.wiro34.hairspray;

import com.github.wiro34.hairspray.factory.FactoryLoader;
import com.github.wiro34.hairspray.factory.InstantiationStrategy;
import com.github.wiro34.hairspray.factory.InstantiationStrategyImpl;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Hairspray {

    private InstantiationStrategyImpl instantiationStrategyImpl = new InstantiationStrategyImpl(new FactoryLoader());

    protected <T> T assemble(Class<T> clazz, InstantiationStrategy strategy, BiConsumer<T, Integer> initializer, int index) {
        return strategy.getInstance(clazz, initializer, index);
    }

    protected <T> T build(Class<T> clazz, BiConsumer<T, Integer> initializer, int index) {
        return assemble(clazz, instantiationStrategyImpl, initializer, index);
    }

    protected <T> T create(Class<T> clazz, BiConsumer<T, Integer> initializer, int index) {
        throw new UnsupportedOperationException("Create operation is not supported");
    }

    /**
     * 指定したクラスのインスタンスを生成します。
     * <p>
     * 生成されたインスタンスは指定されたクラスのファクトリによって初期化されます。
     *
     * @param <T>   生成するクラス
     * @param clazz 生成するクラス
     * @return 生成されたインスタンス
     */
    public <T> T build(Class<T> clazz) {
        return build(clazz, noop());
    }

    /**
     * 指定したクラスのインスタンスを生成します。
     * <p>
     * 生成されたインスタンスは指定されたクラスのファクトリによって初期化されます。
     *
     * @param <T>         生成するクラス
     * @param clazz       生成するクラス
     * @param initializer 再初期化処理
     * @return 生成されたインスタンス
     */
    public <T> T build(Class<T> clazz, Consumer<T> initializer) {
        return build(clazz, (t, n) -> initializer.accept(t), 0);
    }

    /**
     * 指定したクラスのインスタンスを指定された数だけ生成します。
     * <p>
     * 生成されたインスタンスは指定されたクラスのファクトリによって初期化されます。
     *
     * @param <T>   生成するクラス
     * @param clazz 生成するクラス
     * @param size  生成するインスタンスの数
     * @return 生成されたインスタンスのリスト
     */
    public <T> List<T> buildList(Class<T> clazz, int size) {
        return buildList(clazz, size, noop());
    }

    /**
     * 指定したクラスのインスタンスを指定された数だけ生成します。
     * <p>
     * 生成されたインスタンスは指定されたクラスのファクトリによって初期化されます。
     * インスタンスが生成された後、さらに initializer によって再初期化を行います。
     *
     * @param <T>         生成するクラス
     * @param clazz       生成するクラス
     * @param size        生成するインスタンスの数
     * @param initializer 再初期化処理
     * @return 生成されたインスタンスのリスト
     */
    public <T> List<T> buildList(Class<T> clazz, int size, Consumer<T> initializer) {
        return Stream
                .generate(() -> build(clazz, initializer))
                .limit(size)
                .collect(Collectors.toList());
    }

    /**
     * 指定したクラスのインスタンスを指定された数だけ生成します。
     * <p>
     * 生成されたインスタンスは指定されたクラスのファクトリによって初期化されます。
     * インスタンスが生成された後、さらに initializer によって再初期化を行います。
     * initializer には生成されたインスタンスと要素番号が渡されます。
     *
     * @param <T>         生成するクラス
     * @param clazz       生成するクラス
     * @param size        生成するインスタンスの数
     * @param initializer 再初期化処理
     * @return 生成されたインスタンスのリスト
     */
    public <T> List<T> buildList(Class<T> clazz, int size, BiConsumer<T, Integer> initializer) {
        return IntStream.range(0, size)
                .mapToObj(n -> build(clazz, initializer, n))
                .collect(Collectors.toList());
    }

    /**
     * 指定したクラスのインスタンスを生成し、永続化します。
     * <p>
     * 生成されたインスタンスは指定されたクラスのファクトリによって初期化されます。
     * インスタンスが生成された後、さらに initializer によって再初期化を行います。
     *
     * @param <T>         生成するクラス
     * @param clazz       生成するクラス
     * @param initializer 再初期化処理
     * @return 生成されたインスタンスのリスト
     */
    public <T> T create(Class<T> clazz, Consumer<T> initializer) {
        return create(clazz, (t, n) -> initializer.accept(t), 0);
    }

    /**
     * 指定したクラスのインスタンスを生成し、永続化します。
     * <p>
     * 生成されたインスタンスは指定されたクラスのファクトリによって初期化されます。
     *
     * @param <T>   生成するクラス
     * @param clazz 生成するクラス
     * @return 生成されたインスタンスのリスト
     */
    public <T> T create(Class<T> clazz) {
        return create(clazz, noop());
    }

    /**
     * 指定したクラスのインスタンスを生成し、永続化します。
     * <p>
     * 生成されたインスタンスは指定されたクラスのファクトリによって初期化されます。
     *
     * @param <T>   生成するクラス
     * @param clazz 生成するクラス
     * @param size  生成するインスタンスの数
     * @return 生成されたインスタンスのリスト
     */
    public <T> List<T> createList(Class<T> clazz, int size) {
        return createList(clazz, size, noop());
    }

    /**
     * 指定したクラスのインスタンスを生成し、永続化します。
     * <p>
     * 生成されたインスタンスは指定されたクラスのファクトリによって初期化されます。
     * インスタンスが生成された後、さらに initializer によって再初期化を行い、その後永続化します。
     *
     * @param <T>         生成するクラス
     * @param clazz       生成するクラス
     * @param size        生成するインスタンスの数
     * @param initializer 再初期化処理
     * @return 生成されたインスタンスのリスト
     */
    public <T> List<T> createList(Class<T> clazz, int size, Consumer<T> initializer) {
        return Stream
                .generate(() -> create(clazz, initializer))
                .limit(size)
                .collect(Collectors.toList());
    }

    /**
     * 指定したクラスのインスタンスを生成し、永続化します。
     * <p>
     * 生成されたインスタンスは指定されたクラスのファクトリによって初期化されます。
     * インスタンスが生成された後、さらに initializer によって再初期化を行い、その後永続化します。
     * initializer には生成されたインスタンスと要素番号が渡されます。
     *
     * @param <T>         生成するクラス
     * @param clazz       生成するクラス
     * @param size        生成するインスタンスの数
     * @param initializer 再初期化処理
     * @return 生成されたインスタンスのリスト
     */
    public <T> List<T> createList(Class<T> clazz, int size, BiConsumer<T, Integer> initializer) {
        return IntStream.range(0, size)
                .mapToObj(n -> create(clazz, initializer, n))
                .collect(Collectors.toList());
    }

    private static <T> Consumer<T> noop() {
        return (t) -> {
        };
    }
}
