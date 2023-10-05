package com.progamming.apigateway.exception;

public final class NotPermissionException extends RuntimeException{
    public NotPermissionException(final String message) {
        super(message);
    }
}
