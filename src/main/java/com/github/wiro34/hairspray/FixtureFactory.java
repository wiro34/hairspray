package com.github.wiro34.hairspray;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface FixtureFactory {

    /**
     * 指定したクラスのインスタンスを生成します。
     *
     * 生成されたインスタンスは指定されたクラスのファクトリによって初期化されます。 詳しくは {
     *
     * @see Factory} の JavaDoc を参照してください。
     *
     * @param <T> 生成するクラス
     * @param clazz 生成するクラス
     * @return 生成されたインスタンス
     * @see Factory
     */
    public default <T> T build(Class<T> clazz) {
        return build(clazz, (t) -> {
        });
    }

    /**
     * 指定したクラスのインスタンスを生成します。
     *
     * 生成されたインスタンスは指定されたクラスのファクトリによって初期化されます。 詳しくは {
     *
     * @see Factory} の JavaDoc を参照してください。
     *
     * インスタンスが生成された後、さらに initializer によって再初期化を行います。
     *
     * @param <T> 生成するクラス
     * @param clazz 生成するクラス
     * @param initializer 再初期化処理
     * @return 生成されたインスタンス
     * @see Factory
     */
    public <T> T build(Class<T> clazz, Consumer<T> initializer);

    /**
     * 指定したクラスのインスタンスを指定された数だけ生成します。
     *
     * 生成されたインスタンスは指定されたクラスのファクトリによって初期化されます。 詳しくは {
     *
     * @see Factory} の JavaDoc を参照してください。
     *
     * @param <T> 生成するクラス
     * @param clazz 生成するクラス
     * @param size 生成するインスタンスの数
     * @return 生成されたインスタンスのリスト
     * @see Factory
     */
    public default <T> List<T> buildList(Class<T> clazz, int size) {
        return buildList(clazz, size, (t) -> {
        });
    }

    /**
     * 指定したクラスのインスタンスを指定された数だけ生成します。
     *
     * 生成されたインスタンスは指定されたクラスのファクトリによって初期化されます。 詳しくは {
     *
     * @see Factory} の JavaDoc を参照してください。
     *
     * インスタンスが生成された後、さらに initializer によって再初期化を行います。
     *
     * @param <T> 生成するクラス
     * @param clazz 生成するクラス
     * @param size 生成するインスタンスの数
     * @param initializer 再初期化処理
     * @return 生成されたインスタンスのリスト
     * @see Factory
     */
    public default <T> List<T> buildList(Class<T> clazz, int size, Consumer<T> initializer) {
        return Stream
                .generate(() -> build(clazz, initializer))
                .limit(size)
                .collect(Collectors.toList());
    }

    /**
     * 指定したクラスのインスタンスを生成し、永続化します。
     *
     * 生成されたインスタンスは指定されたクラスのファクトリによって初期化されます。 詳しくは {
     *
     * @see Factory} の JavaDoc を参照してください。
     *
     * インスタンスが生成された後、さらに initializer によって再初期化を行います。
     *
     * @param <T> 生成するクラス
     * @param clazz 生成するクラス
     * @param initializer 再初期化処理
     * @return 生成されたインスタンスのリスト
     * @see Factory
     */
    public <T> T create(Class<T> clazz, Consumer<T> initializer);

    /**
     * 指定したクラスのインスタンスを生成し、永続化します。
     *
     * 生成されたインスタンスは指定されたクラスのファクトリによって初期化されます。 詳しくは {
     *
     * @see Factory} の JavaDoc を参照してください。
     *
     * @param <T> 生成するクラス
     * @param clazz 生成するクラス
     * @return 生成されたインスタンスのリスト
     * @see Factory
     */
    public default <T> T create(Class<T> clazz) {
        return create(clazz, (t) -> {
        });
    }

    /**
     * 指定したクラスのインスタンスを生成し、永続化します。
     *
     * 生成されたインスタンスは指定されたクラスのファクトリによって初期化されます。 詳しくは {
     *
     * @see Factory} の JavaDoc を参照してください。
     *
     * @param <T> 生成するクラス
     * @param clazz 生成するクラス
     * @param size 生成するインスタンスの数
     * @return 生成されたインスタンスのリスト
     * @see Factory
     */
    public default <T> List<T> createList(Class<T> clazz, int size) {
        return createList(clazz, size, (t) -> {
        });
    }

    /**
     * 指定したクラスのインスタンスを生成し、永続化します。
     *
     * 生成されたインスタンスは指定されたクラスのファクトリによって初期化されます。 詳しくは {
     *
     * @see Factory} の JavaDoc を参照してください。
     *
     * インスタンスが生成された後、さらに initializer によって再初期化を行い、 その後永続化します。
     *
     * @param <T> 生成するクラス
     * @param clazz 生成するクラス
     * @param size 生成するインスタンスの数
     * @param initializer 再初期化処理
     * @return 生成されたインスタンスのリスト
     * @see Factory
     */
    public default <T> List<T> createList(Class<T> clazz, int size, Consumer<T> initializer) {
        return Stream
                .generate(() -> create(clazz, initializer))
                .limit(size)
                .collect(Collectors.toList());
    }
}
