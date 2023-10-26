package com.lms.authservice.client;

import com.lms.authservice.model.CourseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange
public interface CourseClient {

    @GetExchange(value = "/api/v1/lms/courses")
    public ResponseEntity<?> getAllCourses();

    @GetExchange(value = "/api/v1/lms/courses/{courseId}")
    public ResponseEntity<?> getCourseById(@PathVariable String courseId);

    @GetExchange(value = "/api/v1/lms/courses/name/{name}")
    public ResponseEntity<?> getCourseByName(@PathVariable String name);

    @GetExchange(value = "/api/v1/lms/courses/info/{technology}")
    public ResponseEntity<?> getCourseByTech(@PathVariable String technology);

    @GetExchange(value = "/api/v1/lms/courses/info/{technology}/{durationFrom}/{durationTo}")
    public ResponseEntity<?> getCourseByTechAndDuration(@PathVariable String technology, @PathVariable String durationFrom, @PathVariable String durationTo);

    @PostExchange(value="/api/v1/lms/courses")
    public ResponseEntity<?> saveCourse(@RequestBody CourseDTO courseDTO);
    @DeleteExchange(value = "/api/v1/lms/courses/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable String courseId);

}
