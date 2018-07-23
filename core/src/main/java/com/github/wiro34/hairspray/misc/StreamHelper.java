package com.github.wiro34.hairspray.misc;

import java.util.function.Function;
import java.util.function.Predicate;

public class StreamHelper {

    @FunctionalInterface
    public interface ThrowingFunction<T, R> {
        R apply(T t) throws Exception;
    }

    @FunctionalInterface
    public interface ThrowingPredicate<T> {
        boolean test(T t) throws Exception;
    }

    public static class StreamExceptionWrapper extends RuntimeException {

        StreamExceptionWrapper(Exception e) {
            super(e);
        }

    }

    private StreamHelper() {
    }

    public static <T, R> Function<T, R> throwingFunction(ThrowingFunction<T, R> target) {
        return (arg -> {
            try {
                return target.apply(arg);
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e) {
                throw new StreamExceptionWrapper(e);
            }
        });
    }

    public static <T> Predicate<T> throwingPredicate(ThrowingPredicate<T> target) {
        return (arg -> {
            try {
                return target.test(arg);
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e) {
                throw new StreamExceptionWrapper(e);
            }
        });
    }

}
