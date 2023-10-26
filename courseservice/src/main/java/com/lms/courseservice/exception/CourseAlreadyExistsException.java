package com.lms.courseservice.exception;

import org.springframework.stereotype.Component;

@Component
public class CourseAlreadyExistsException extends Exception{

    public CourseAlreadyExistsException() {
    }

    public CourseAlreadyExistsException(String message) {
        super(message);
    }
}
