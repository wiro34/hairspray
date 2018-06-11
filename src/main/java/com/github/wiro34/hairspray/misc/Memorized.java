package com.github.wiro34.hairspray.misc;

import java.util.function.Supplier;

public class Memorized< ReturnValue> {
    private final Supplier<ReturnValue> supplier;
    private ReturnValue memorized;

    public Memorized(Supplier<ReturnValue> supplier) {
        this.supplier = supplier;
    }

    public ReturnValue get() {
        if (memorized == null) {
            memorized = supplier.get();
        }
        return memorized;
    }
}
