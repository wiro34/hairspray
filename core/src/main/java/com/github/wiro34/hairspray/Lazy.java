package com.github.wiro34.hairspray;

import java.util.function.Function;

/**
 * インスタンスが生成・永続化された後に実行される初期化処理を定義します。
 */
public interface Lazy<T, U> extends Function<T, U> {

}
