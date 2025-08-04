package com.example.odgateway.infrastructure.exception;

import lombok.Getter;

@Getter
public class CustomConnectionRefusedException extends RuntimeException {

    private final ErrorCode errorCode;

    public CustomConnectionRefusedException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
