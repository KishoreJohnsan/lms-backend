package com.lms.authservice.exception;

import org.springframework.stereotype.Component;

@Component
public class UserAlreadyExistsException extends Exception {

    public UserAlreadyExistsException() {
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
