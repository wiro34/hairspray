package com.github.wiro34.hairspray;

import com.github.wiro34.hairspray.annotation.Factory;
import com.github.wiro34.hairspray.factory.strategy.BuildStrategy;
import com.github.wiro34.hairspray.factory.strategy.CreateStrategy;
import com.github.wiro34.hairspray.factory.strategy.InstantiationStrategy;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@ApplicationScoped
public class Hairspray {

    @Inject
    private BuildStrategy buildStrategy = new BuildStrategy();

    @Inject
    private CreateStrategy createStrategy = new CreateStrategy();

    private <T> T assemble(Class<T> clazz, InstantiationStrategy strategy, BiConsumer<T, Integer> initializer, int index) {
        return strategy.getInstance(clazz, initializer, index);
    }

    private <T> T build(Class<T> clazz, BiConsumer<T, Integer> initializer, int index) {
        return assemble(clazz, buildStrategy, initializer, index);
    }

    private <T> T create(Class<T> clazz, BiConsumer<T, Integer> initializer, int index) {
        return assemble(clazz, createStrategy, initializer, index);
    }

    /**
     * 指定したクラスのインスタンスを生成します。
     * <p>
     * 生成されたインスタンスは指定されたクラスのファクトリによって初期化されます。
     *
     * @param <T>   生成するクラス
     * @param clazz 生成するクラス
     * @return 生成されたインスタンス
     * @see Factory } の JavaDoc を参照してください。
     * @see Factory
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
     * @see Factory} の JavaDoc を参照してください。
     * <p>
     * インスタンスが生成された後、さらに initializer によって再初期化を行います。
     * @see Factory
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
     * @see Factory} の JavaDoc を参照してください。
     * @see Factory
     */
    public <T> List<T> buildList(Class<T> clazz, int size) {
        return buildList(clazz, size, noop());
    }

    /**
     * 指定したクラスのインスタンスを指定された数だけ生成します。
     * <p>
     * 生成されたインスタンスは指定されたクラスのファクトリによって初期化されます。
     *
     * @param <T>         生成するクラス
     * @param clazz       生成するクラス
     * @param size        生成するインスタンスの数
     * @param initializer 再初期化処理
     * @return 生成されたインスタンスのリスト
     * @see Factory} の JavaDoc を参照してください。
     * <p>
     * インスタンスが生成された後、さらに initializer によって再初期化を行います。
     * @see Factory
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
     *
     * @param <T>         生成するクラス
     * @param clazz       生成するクラス
     * @param size        生成するインスタンスの数
     * @param initializer 再初期化処理
     * @return 生成されたインスタンスのリスト
     * @see Factory} の JavaDoc を参照してください。
     * <p>
     * インスタンスが生成された後、さらに initializer によって再初期化を行います。 initializer
     * には生成されたインスタンスと要素番号が渡されます。
     * @see Factory
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
     *
     * @param <T>         生成するクラス
     * @param clazz       生成するクラス
     * @param initializer 再初期化処理
     * @return 生成されたインスタンスのリスト
     * @see Factory} の JavaDoc を参照してください。
     * <p>
     * インスタンスが生成された後、さらに initializer によって再初期化を行います。
     * @see Factory
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
     * @see Factory} の JavaDoc を参照してください。
     * @see Factory
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
     * @see Factory} の JavaDoc を参照してください。
     * @see Factory
     */
    public <T> List<T> createList(Class<T> clazz, int size) {
        return createList(clazz, size, noop());
    }

    /**
     * 指定したクラスのインスタンスを生成し、永続化します。
     * <p>
     * 生成されたインスタンスは指定されたクラスのファクトリによって初期化されます。
     *
     * @param <T>         生成するクラス
     * @param clazz       生成するクラス
     * @param size        生成するインスタンスの数
     * @param initializer 再初期化処理
     * @return 生成されたインスタンスのリスト
     * @see Factory} の JavaDoc を参照してください。
     * <p>
     * インスタンスが生成された後、さらに initializer によって再初期化を行い、 その後永続化します。
     * @see Factory
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
     *
     * @param <T>         生成するクラス
     * @param clazz       生成するクラス
     * @param size        生成するインスタンスの数
     * @param initializer 再初期化処理
     * @return 生成されたインスタンスのリスト
     * @see Factory} の JavaDoc を参照してください。
     * <p>
     * インスタンスが生成された後、さらに initializer によって再初期化を行い、 その後永続化します。 initializer
     * には生成されたインスタンスと要素番号が渡されます。
     * @see Factory
     */
    public <T> List<T> createList(Class<T> clazz, int size, BiConsumer<T, Integer> initializer) {
        return IntStream.range(0, size)
                .mapToObj(n -> create(clazz, initializer, n))
                .collect(Collectors.toList());
    }

    private <T> Consumer<T> noop() {
        return (t) -> {
        };
    }
}
