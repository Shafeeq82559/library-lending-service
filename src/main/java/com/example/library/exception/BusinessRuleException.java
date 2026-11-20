package com.example.library.exception;

import org.springframework.http.HttpStatus;

public class BusinessRuleException extends RuntimeException {

    private final HttpStatus status;

    public BusinessRuleException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public static BusinessRuleException notFound(String what, Long id) {
        return new BusinessRuleException(HttpStatus.NOT_FOUND, what + " " + id + " not found");
    }

    public static BusinessRuleException conflict(String message) {
        return new BusinessRuleException(HttpStatus.CONFLICT, message);
    }

    public static BusinessRuleException rejected(String message) {
        return new BusinessRuleException(HttpStatus.UNPROCESSABLE_ENTITY, message);
    }

    public HttpStatus getStatus() {
        return status;
    }
}
