package com.lms.courseservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CourseDTO {

    private String id;
    private String name;
    private String technology;
    private String duration;
    private String description;
    private String url;

    public CourseDTO(CourseBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.technology = builder.technology;
        this.duration = builder.duration;
        this.description = builder.description;
        this.url = builder.url;
    }

    public static class CourseBuilder {
        private String id;
        private String name;
        private String technology;
        private String duration;
        private String description;
        private String url;

        public CourseBuilder(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public CourseBuilder withTechnology(String technology) {
            this.technology = technology;
            return this;
        }

        public CourseBuilder withDuration(String duration) {
            this.duration = duration;
            return this;
        }

        public CourseBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public CourseBuilder withURL(String url) {
            this.url = url;
            return this;
        }

        public CourseDTO build() {
            return new CourseDTO(this);
        }
    }

}

