package com.lms.courseservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.courseservice.model.CourseDTO;
import com.lms.courseservice.model.CustomResponse;
import com.lms.courseservice.service.CourseService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CourseControllerTest {

    @Autowired
    private MockMvc mvc;

    @Mock
    private CourseService service;
    @Autowired
    private ObjectMapper mapper;
    private final String baseUrl = "http://localhost:3060/api/v1/lms/courses";

    @Test
    void testGetAllCourses() throws Exception {

        List<CourseDTO> list = List.of(new CourseDTO.CourseBuilder("1", "Test").build());
        CustomResponse res = new CustomResponse(Boolean.TRUE, 200, list);
        Mockito.when(service.getAllCourses()).thenReturn(res);
        ResultActions response = mvc.perform(get(baseUrl).contentType(MediaType.APPLICATION_JSON));
        response.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

    }

    @Test
    void testGetCourseByID() throws Exception {

        List<CourseDTO> list = List.of(new CourseDTO.CourseBuilder("1", "Test").build());
        CustomResponse res = new CustomResponse(Boolean.TRUE, 200, list);
        Mockito.when(service.getCourseById(Mockito.anyString())).thenReturn(res);
        String url = baseUrl.concat("/1");
        ResultActions response = mvc.perform(get(url).contentType(MediaType.APPLICATION_JSON));
        response.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

    }

    @Test
    void testGetCourseByName() throws Exception {

        List<CourseDTO> list = List.of(new CourseDTO.CourseBuilder("1", "Test").build());
        CustomResponse res = new CustomResponse(Boolean.TRUE, 200, list);
        Mockito.when(service.getCourseByName(Mockito.anyString())).thenReturn(res);
        String url = baseUrl.concat("/name/abc");
        ResultActions response = mvc.perform(get(url).contentType(MediaType.APPLICATION_JSON));
        response.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

    }

    @Test
    void testGetCourseByTech() throws Exception {

        List<CourseDTO> list = List.of(new CourseDTO.CourseBuilder("1", "Test").build());
        CustomResponse res = new CustomResponse(Boolean.TRUE, 200, list);
        Mockito.when(service.getCourseByTech(Mockito.anyString())).thenReturn(res);
        String url = baseUrl.concat("/info/abc");
        ResultActions response = mvc.perform(get(url).contentType(MediaType.APPLICATION_JSON));
        response.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

    }

    @Test
    void testGetCourseByTechAndDuration() throws Exception {

        List<CourseDTO> list = List.of(new CourseDTO.CourseBuilder("1", "Test").build());
        CustomResponse res = new CustomResponse(Boolean.TRUE, 200, list);
        Mockito.when(service.getCourseByTechAndDuration(Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(res);
        String url = baseUrl.concat("/info/abc/0/1");
        ResultActions response = mvc.perform(get(url).contentType(MediaType.APPLICATION_JSON));
        response.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

    }

    @Test
    void testSaveCourse() throws Exception {


        CourseDTO courseDTO = new CourseDTO.CourseBuilder("1", "Test").build();
        CustomResponse res = new CustomResponse(Boolean.TRUE, 201, "Course Added");
        Mockito.when(service.saveCourse(courseDTO)).thenReturn(res);

        ResultActions response = mvc.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(courseDTO)));
        response.andExpect(status().isCreated()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

    }

    @Test
    void testDeleteCourse() throws Exception {

        CustomResponse res = new CustomResponse(Boolean.TRUE, 200, "Course Deleted");
        Mockito.when(service.deleteCourse(Mockito.anyString())).thenReturn(res);
        String url = baseUrl.concat("/1");
        ResultActions response = mvc.perform(delete(url).contentType(MediaType.APPLICATION_JSON));
        response.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

    }


}
