package com.github.wiro34.hairspray.exception;

public class RuntimeInstantiationException extends RuntimeException {

    private static final long serialVersionUID = 5991000691942061156L;

    public RuntimeInstantiationException(Throwable cause) {
        super(cause);
    }
    
    public RuntimeInstantiationException(String message) {
        super(message);
    }
}
