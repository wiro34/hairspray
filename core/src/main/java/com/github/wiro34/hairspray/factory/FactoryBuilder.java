package com.github.wiro34.hairspray.factory;

/**
 * ファクトリのインスタンスを取得するメソッドを提供するインターフェイス
 */
public interface FactoryBuilder {

    /**
     * 指定したクラスのファクトリのインスタンスを返します。
     *
     * @param factoryClass ファクトリのクラス
     * @return ファクトリのインスタンス
     */
    <T> T getFactoryInstance(Class<T> factoryClass);

}
