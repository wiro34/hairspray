package com.github.wiro34.hairspray;

import com.github.wiro34.hairspray.exception.PropertyManufactureException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class InstanceAssembler {

    private final Class<?> modelClass;
    private final Field[] modelFields;

    public InstanceAssembler(Class<?> modelClass) {
        this.modelClass = modelClass;
        this.modelFields = modelClass.getDeclaredFields();
    }

    public void assemble(Object instance, Object factory) {
        copyInstantFields(instance, factory);
        copyLazyFields(instance, factory);
    }

    public void copyInstantFields(Object instance, Object factory) {
        copyFields(instance, factory, (f) -> !f.getType().equals(Function.class));
    }

    public void copyLazyFields(Object instance, Object factory) {
        copyFields(instance, factory, (f) -> f.getType().equals(Function.class));
    }

    private void copyFields(Object instance, Object factory, Predicate<Field> fieldCondition) {
        List<String> factoryFields = Arrays.stream(factory.getClass().getDeclaredFields())
                .filter(fieldCondition)
                .map(field -> field.getName())
                .collect(Collectors.toList());
        Arrays.stream(modelFields)
                .filter((field) -> factoryFields.contains(field.getName()))
                .forEach((field) -> copyFactoryValueIfNull(instance, factory, field));
    }

    private void copyFactoryValueIfNull(Object instance, Object factory, Field field) {
        try {
            Method getter = getGetterMethod(modelClass, field);
            if (getter.invoke(instance) != null) {
                return;
            }

            Method setter = getSetterMethod(modelClass, field);
            Field factoryField = factory.getClass().getDeclaredField(field.getName());
            if (factoryField.getType().equals(Function.class)) {
                setter.invoke(instance, ((Function) factoryField.get(factory)).apply(instance));
            } else {
                setter.invoke(instance, factoryField.get(factory));
            }
        } catch (SecurityException | IllegalArgumentException | NoSuchFieldException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new PropertyManufactureException(e);
        }
    }

    private Method getGetterMethod(Class<?> clazz, Field field) throws NoSuchMethodException {
        return clazz.getMethod(getAccessorMethodName("get", field));
    }

    private Method getSetterMethod(Class<?> clazz, Field field) throws NoSuchMethodException {
        return clazz.getMethod(getAccessorMethodName("set", field), field.getType());
    }

    private String getAccessorMethodName(String accessorType, Field field) throws NoSuchMethodException {
        String fieldName = field.getName();
        return accessorType + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }
}
