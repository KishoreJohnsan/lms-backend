package com.lms.courseservice.service;

import com.lms.courseservice.entity.Course;
import com.lms.courseservice.exception.CourseAlreadyExistsException;
import com.lms.courseservice.exception.CourseNotFoundException;
import com.lms.courseservice.model.CourseDTO;
import com.lms.courseservice.model.CustomResponse;
import com.lms.courseservice.repo.CourseRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    CourseRepo repo;

    @Autowired
    private ModelMapper modelMapper;

    public CustomResponse getAllCourses() throws CourseNotFoundException {

        List<Course> courseList = repo.findAll();
        if (courseList.isEmpty())
            throw new CourseNotFoundException("Empty Courses");
        else {
            List<CourseDTO> result = courseList.stream().map(course ->
                            new CourseDTO.CourseBuilder(course.getId(), course.getName())
                                    .withDuration(course.getDuration())
                                    .withTechnology(course.getTechnology())
                                    .withDescription(course.getDescription())
                                    .withURL(course.getUrl())
                                    .build())
                    .toList();
            return buildResponse(result);
        }
    }

    public CustomResponse getCourseById(String courseId) throws CourseNotFoundException {
        Optional<Course> courseOpt = repo.findById(courseId);
        if (courseOpt.isPresent()) {
            Course course = courseOpt.get();
            CourseDTO result = new CourseDTO.CourseBuilder(course.getId(), course.getName())
                    .withDuration(course.getDuration())
                    .withTechnology(course.getTechnology())
                    .withDescription(course.getDescription())
                    .withURL(course.getUrl())
                    .build();
            return buildResponse(result);
        } else throw new CourseNotFoundException("Selected Course not found");
    }

    public CustomResponse getCourseByName(String name) throws CourseNotFoundException {
        List<Course> courseList = repo.findByNameLikeIgnoreCase(name);
        if (courseList.isEmpty())
            throw new CourseNotFoundException("Course not found");
        else {
            List<CourseDTO> result =  courseList.stream().map(course ->
                            new CourseDTO.CourseBuilder(course.getId(), course.getName())
                                    .withDuration(course.getDuration())
                                    .withTechnology(course.getTechnology())
                                    .withDescription(course.getDescription())
                                    .withURL(course.getUrl())
                                    .build())
                    .toList();
            return buildResponse(result);
        }
    }

    public CustomResponse getCourseByTech(String technology) throws CourseNotFoundException {
        List<Course> courseList = repo.findByTechnologyLikeIgnoreCase(technology);
        if (courseList.isEmpty())
            throw new CourseNotFoundException("Course not found");
        else {
            List<CourseDTO> result =  courseList.stream().map(course ->
                            new CourseDTO.CourseBuilder(course.getId(), course.getName())
                                    .withDuration(course.getDuration())
                                    .withTechnology(course.getTechnology())
                                    .withDescription(course.getDescription())
                                    .withURL(course.getUrl())
                                    .build())
                    .toList();
            return buildResponse(result);
        }
    }

    public CustomResponse getCourseByTechAndDuration(String technology, String durationFrom, String durationTo) throws CourseNotFoundException {
        List<Course> courseList = repo.findByTechnologyAndDurationLikeIgnoreCase(technology, durationFrom, durationTo);

        if (courseList.isEmpty()) throw new CourseNotFoundException("No courses found by selected criteria");
        else {
            List<CourseDTO> result =  courseList.stream().map(course ->
                            new CourseDTO.CourseBuilder(course.getId(), course.getName())
                                    .withDuration(course.getDuration())
                                    .withTechnology(course.getTechnology())
                                    .withDescription(course.getDescription())
                                    .withURL(course.getUrl())
                                    .build())
                    .toList();
            return buildResponse(result);
        }

    }

    public CustomResponse saveCourse(CourseDTO courseDTO) throws CourseAlreadyExistsException {
        Optional<Course> courseOpt = repo.findByNameAndTechnologyAndDuration(courseDTO.getName(), courseDTO.getTechnology(), courseDTO.getDuration());
        if (courseOpt.isPresent())
            throw new CourseAlreadyExistsException("Course Already Exists");
        else {

            Course course = modelMapper.map(courseDTO, Course.class);
            repo.save(course);
        }
        return buildResponse("Course Added");
    }

    public CustomResponse deleteCourse(String courseId) throws CourseNotFoundException {
        Optional<Course> courseOpt = repo.findById(courseId);
        if (courseOpt.isPresent())
            repo.deleteById(courseId);
        else
            throw new CourseNotFoundException("Selected Course not found");

        return buildResponse("Course Deleted");
    }

    private CustomResponse buildResponse(Object object) {
        CustomResponse response = new CustomResponse();
        response.setResponse(object);
        response.setStatus(true);
        response.setErrorCode(200);

        return response;

    }
}
