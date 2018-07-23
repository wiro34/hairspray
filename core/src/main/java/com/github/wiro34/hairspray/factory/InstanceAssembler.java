package com.github.wiro34.hairspray.factory;

import com.github.wiro34.hairspray.Dynamic;
import com.github.wiro34.hairspray.Lazy;
import com.github.wiro34.hairspray.exception.PropertyManufactureException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static com.github.wiro34.hairspray.misc.StreamHelper.throwingFunction;
import static com.github.wiro34.hairspray.misc.StreamHelper.throwingPredicate;

/**
 * インスタンスの各フィールドをファクトリに従って初期化するクラス
 */
public class InstanceAssembler {

    private final Class<?> modelClass;

    private final Object factory;

    public InstanceAssembler(Class<?> modelClass, Object factory) {
        this.modelClass = modelClass;
        this.factory = factory;
    }

    public void assembleInstantFields(Object instance) {
        copyFields(instance, factory, (f) -> !(isInstanceOf(f, Function.class, Dynamic.class, Lazy.class)));
    }

    public void assembleDynamicFields(Object instance) {
        copyFields(instance, factory, (f) -> isInstanceOf(f, Function.class, Dynamic.class));
    }

    public void assembleLazyFields(Object instance) {
        copyFields(instance, factory, throwingPredicate((f) ->
                isInstanceOf(f, Lazy.class) &&
                        getGetterMethod(modelClass.getDeclaredField(f.getName())).invoke(instance) == null
        ));
    }

    private void copyFields(Object instance, Object factory, Predicate<Field> condition) {
        getDeclaredFieldsExcludingSynthetics(factory.getClass())
                .filter(condition)
                .filter(this::matchFields)
                .map(throwingFunction(field -> new FieldPair(modelClass.getDeclaredField(field.getName()), field)))
                .forEach((pair) -> {
                    copyFactoryValue(instance, factory, pair);
                });
    }

    @SuppressWarnings("unchecked")
    private void copyFactoryValue(Object instance, Object factory, FieldPair pair) {
        try {
            Field factoryField = factory.getClass().getDeclaredField(pair.modelField.getName());
            if (isInstanceOf(factoryField, Function.class, Dynamic.class, Lazy.class)) {
                setInternalState(instance, pair, ((Function) factoryField.get(factory)).apply(instance));
            } else {
                setInternalState(instance, pair, factoryField.get(factory));
            }
        } catch (SecurityException | NoSuchFieldException | IllegalAccessException e) {
            throw new PropertyManufactureException(e);
        }
    }

    private void setInternalState(Object instance, FieldPair pair, Object value) {
        try {
            Method setter = getSetterMethod(pair.modelField);
            setter.invoke(instance, value);
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            throw new PropertyManufactureException(e);
        }
    }

    private Method getSetterMethod(Field field) throws NoSuchMethodException {
        return modelClass.getMethod(getAccessorMethodName("set", field), field.getType());
    }

    private Method getGetterMethod(Field field) throws NoSuchMethodException {
        return modelClass.getMethod(getAccessorMethodName("get", field));
    }

    private String getAccessorMethodName(String accessorType, Field field) throws NoSuchMethodException {
        String fieldName = field.getName();
        return accessorType + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    private Stream<Field> getDeclaredFieldsExcludingSynthetics(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isSynthetic());
    }

    private boolean isInstanceOf(Field field, Class<?>... classes) {
        return Stream.of(classes)
                .anyMatch(clazz -> clazz.equals(field.getType()));
    }

    private boolean matchFields(Field factoryField) {
        return Stream.of(modelClass.getDeclaredFields())
                .anyMatch(f -> f.getName().equals(factoryField.getName()));
    }

    private static class FieldPair {

        final Field modelField;
        final Field factoryField;

        FieldPair(Field modelField, Field factoryField) {
            this.modelField = modelField;
            this.factoryField = factoryField;
        }
    }
}
