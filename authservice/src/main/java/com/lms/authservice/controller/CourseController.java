package com.lms.authservice.controller;

import com.lms.authservice.client.CourseClient;
import com.lms.authservice.model.CourseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/lms/courses")
public class CourseController {

    @Autowired
    private CourseClient courseClient;

    @GetMapping(value = "/getall")
    public ResponseEntity<?> getCourseList() {
        return new ResponseEntity<>(courseClient.getAllCourses().getBody(), HttpStatus.OK);
    }

    @GetMapping(value = "/{courseId}")
    public ResponseEntity<?> getCourseById(@PathVariable String courseId) {
        return new ResponseEntity<>(courseClient.getCourseById(courseId).getBody(), HttpStatus.OK);
    }

    @GetMapping(value = "/name/{name}")
    public ResponseEntity<?> getCourseByName(@PathVariable String name) {
        return new ResponseEntity<>(courseClient.getCourseByName(name).getBody(), HttpStatus.OK);
    }

    @GetMapping(value = "/info/{technology}")
    public ResponseEntity<?> getCourseByTech(@PathVariable() String technology) {
        return new ResponseEntity<>(courseClient.getCourseByTech(technology).getBody(), HttpStatus.OK);
    }

    @GetMapping(value = "/get/{technology}/{durationFrom}/{durationTo}")
    public ResponseEntity<?> getCourseByTechAndDuration(@PathVariable String technology, @PathVariable String durationFrom, @PathVariable String durationTo) {
        return new ResponseEntity<>(courseClient.getCourseByTechAndDuration(technology, durationFrom, durationTo).getBody(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> saveCourse(@RequestBody CourseDTO courseDTO) {
        return new ResponseEntity<>(courseClient.saveCourse(courseDTO).getBody(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable String courseId) {
        return new ResponseEntity<>(courseClient.deleteCourse(courseId).getBody(), HttpStatus.OK);
    }

}

