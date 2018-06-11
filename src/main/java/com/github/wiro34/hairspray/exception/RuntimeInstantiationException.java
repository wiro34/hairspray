package com.github.wiro34.hairspray.exception;

public class RuntimeInstantiationException extends RuntimeException {

    public RuntimeInstantiationException(Throwable cause) {
        super(cause);
    }
    
    public RuntimeInstantiationException(String message) {
        super(message);
    }
}
