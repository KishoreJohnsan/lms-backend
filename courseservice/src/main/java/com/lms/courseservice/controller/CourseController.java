package com.lms.courseservice.controller;

import com.lms.courseservice.exception.CourseAlreadyExistsException;
import com.lms.courseservice.exception.CourseNotFoundException;
import com.lms.courseservice.model.CourseDTO;
import com.lms.courseservice.model.CustomResponse;
import com.lms.courseservice.repo.CourseRepo;
import com.lms.courseservice.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/lms/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseRepo repo;

    @Autowired
    private Environment env;

    @GetMapping()
    public ResponseEntity<CustomResponse> getAllCourses() throws CourseNotFoundException {
        return new ResponseEntity<>(courseService.getAllCourses(), HttpStatus.OK);
    }

    @GetMapping(value = "/{courseId}")
    public ResponseEntity<CustomResponse> getCourseById(@PathVariable String courseId) throws CourseNotFoundException {
        return new ResponseEntity<>(courseService.getCourseById(courseId), HttpStatus.OK);
    }

    @GetMapping(value = "/name/{name}")
    public ResponseEntity<CustomResponse> getCourseByName(@PathVariable String name) throws CourseNotFoundException {
        return new ResponseEntity<>(courseService.getCourseByName(name), HttpStatus.OK);
    }

    @GetMapping(value = "/info/{technology}")
    public ResponseEntity<CustomResponse> getCourseByTech(@PathVariable String technology) throws CourseNotFoundException {
        return new ResponseEntity<>(courseService.getCourseByTech(technology), HttpStatus.OK);
    }

    @GetMapping(value = "/info/{technology}/{durationFrom}/{durationTo}")
    public ResponseEntity<CustomResponse> getCourseByTechAndDuration(@PathVariable String technology, @PathVariable String durationFrom, @PathVariable String durationTo) throws CourseNotFoundException {

        return new ResponseEntity<>(courseService.getCourseByTechAndDuration(technology, durationFrom, durationTo), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<CustomResponse> saveCourse(@RequestBody CourseDTO courseDTO) throws CourseAlreadyExistsException {
        return new ResponseEntity<>(courseService.saveCourse(courseDTO), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{courseId}")
    public ResponseEntity<CustomResponse> deleteCourse(@PathVariable String courseId) throws CourseNotFoundException {
        return new ResponseEntity<>(courseService.deleteCourse(courseId), HttpStatus.OK);
    }


}
