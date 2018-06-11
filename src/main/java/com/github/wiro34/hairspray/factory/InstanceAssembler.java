package com.github.wiro34.hairspray.factory;

import com.github.wiro34.hairspray.exception.PropertyManufactureException;
import com.github.wiro34.hairspray.factory.strategy.InstantiationStrategy;
import com.google.common.collect.Lists;

import javax.persistence.JoinColumn;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InstanceAssembler {

    private final Class<?> modelClass;

    private final Object factory;

    public InstanceAssembler(Class<?> modelClass, Object factory) {
        this.modelClass = modelClass;
        this.factory = factory;
    }

    public void assembleInstantFields(Object instance) {
        copyFields(instance, factory, (f) -> !f.getType().equals(Function.class));
    }

    public void assembleLazyFields(Object instance) {
        copyFields(instance, factory, (f) -> f.getType().equals(Function.class));
    }

    void associateFields(Object instance) {

    }

    private void copyFields(Object instance, Object factory, Predicate<Field> fieldCondition) {
        List<String> factoryFields = getDeclaredFieldsExcludingSynthetics(factory.getClass())
                .filter(fieldCondition)
                .map(Field::getName)
                .collect(Collectors.toList());
        getDeclaredFieldsExcludingSynthetics(modelClass)
                .filter((field) -> factoryFields.contains(field.getName()))
                .forEach((field) -> copyFactoryValue(instance, factory, field));
    }

    @SuppressWarnings("unchecked")
    private void copyFactoryValue(Object instance, Object factory, Field field) {
        try {
            Field factoryField = factory.getClass().getDeclaredField(field.getName());
            if (factoryField.getType().equals(Function.class)) {
                setInternalState(instance, field, ((Function) factoryField.get(factory)).apply(instance));
            } else {
                setInternalState(instance, field, factoryField.get(factory));
            }
        } catch (SecurityException | NoSuchFieldException | IllegalAccessException e) {
            throw new PropertyManufactureException(e);
        }
    }

    private void setInternalState(Object instance, Field field, Object value) {
        try {
            Method setter = getSetterMethod(modelClass, field);
            setter.invoke(instance, value);
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            throw new PropertyManufactureException(e);
        }
    }

    private Method getSetterMethod(Class<?> clazz, Field field) throws NoSuchMethodException {
        return clazz.getMethod(getAccessorMethodName("set", field), field.getType());
    }

    private String getAccessorMethodName(String accessorType, Field field) throws NoSuchMethodException {
        String fieldName = field.getName();
        return accessorType + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    private Stream<Field> getDeclaredFieldsExcludingSynthetics(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isSynthetic());
    }
}
