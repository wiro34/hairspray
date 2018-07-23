package com.github.wiro34.hairspray;

import java.util.function.Function;

/**
 * ストラテジに渡された初期化処理が実行される前に行う処理を定義します。
 */
public interface Dynamic<T, U> extends Function<T, U> {

}
