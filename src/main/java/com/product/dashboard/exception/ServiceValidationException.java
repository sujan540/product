package com.product.dashboard.exception;

import lombok.Getter;

@Getter
public class ServiceValidationException extends RuntimeException {
    public ServiceValidationException() {
        super();
    }

    public ServiceValidationException(String message) {
        super(message);
    }

    public ServiceValidationException(Throwable cause) {
        super(cause);
    }
}
