package com.github.wiro34.hairspray.misc;

import java.util.function.Supplier;

public class Memorized<T> {

    private final Supplier<T> supplier;
    private T memorized;

    public Memorized(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public T get() {
        if (memorized == null) {
            memorized = supplier.get();
        }
        return memorized;
    }
}
