package com.lms.courseservice.exception;

import org.springframework.stereotype.Component;

@Component
public class CourseNotFoundException extends Exception {

    public CourseNotFoundException() {
    }

    public CourseNotFoundException(String message) {
        super(message);
    }
}
