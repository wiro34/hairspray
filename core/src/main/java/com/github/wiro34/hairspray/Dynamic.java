package com.github.wiro34.hairspray;

import java.util.function.Function;

/**
 * インスタンスが生成された後、永続化される前に実行される初期化処理を定義します。
 */
public interface Dynamic<T, U> extends Function<T, U> {

}
