package com.github.wiro34.hairspray.annotation;

import com.github.wiro34.hairspray.Hairspray;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * インスタンスを初期化するためのファクトリを表します。 このアノテーションが付与されたクラスは、{@link Hairspray} の各メソッドで
 * インスタンスが生成された後、プロパティの設定のために呼びだされます。
 *
 * <p>
 * このアノテーションには、生成対象のクラスを指定してください。 例えば、以下の様に設定します：
 * <pre>
 *     &#064;Factory(User.class)
 *     public class UserFactory {
 *         ...
 *     }
 * </pre>
 *
 * <p>
 * ファクトリのメソッド名は、対象とするクラスのフィールド名と合わせてください。
 * また、メソッドは引数は設定対象のインスタンスを１つだけ取り、設定値を返すよう定義してください。
 * <pre>
 *     public String name(User user) {
 *         return "John Doe";
 *     }
 * </pre>
 *
 * <p>
 * 生成対象となるクラスには、引数を持たないコンストラクタと 各フィールドを設定するセッターメソッドを用意してください。
 */
@Documented
@Retention(RUNTIME)
@Target(ElementType.TYPE)
public @interface Factory {

    Class<?> value();
}
