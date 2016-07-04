package com.github.wiro34.hairspray;

import com.github.wiro34.hairspray.exception.PropertyManufactureException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class InstanceAssembler {

    /**
     * インスタンスにファクトリで生成された値をはめ込みます。
     *
     * インスタンスの各フィールドに対応するファクトリのメソッドを呼び出し、その戻り値をインスタンスに設定します。
     * ファクトリメソッドが見つからない場合、初期化は行われません。
     *
     * このメソッドは引数で渡された instance を更新します。
     *
     * @param <T>
     * @param instance 設定するインスタンス
     * @param factory ファクトリオブジェクト
     * @return 設定済みインスタンス
     */
    public static <T> T assemble(T instance, Object factory) {
        Class<?> modelClass = instance.getClass();
        T assembled = Arrays.stream(modelClass.getDeclaredFields()).reduce(instance, (ins, field) -> {
            final String fieldName = field.getName();
            if (hasMethod(factory, fieldName)) {
                try {
                    Method setter = modelClass.getMethod(setterMethodName(fieldName), field.getType());
                    Method factoryMethod = factory.getClass().getMethod(fieldName, modelClass);
                    setter.invoke(ins, factoryMethod.invoke(factory, ins));
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    throw new PropertyManufactureException(e);
                }
            }
            return ins;
        }, (i1, i2) -> i1);
        return assembled;
    }

    private static String setterMethodName(String fieldName) {
        return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    private static boolean hasMethod(Method[] methods, String methodName) {
        return Arrays
                .stream(methods)
                .filter((method) -> method.getName().equals(methodName))
                .findFirst()
                .isPresent();
    }

    private static boolean hasMethod(Class<?> clazz, String methodName) {
        return hasMethod(clazz.getMethods(), methodName);
    }

    private static boolean hasMethod(Object object, String methodName) {
        return hasMethod(object.getClass(), methodName);
    }
}
