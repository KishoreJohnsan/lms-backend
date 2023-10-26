package com.lms.courseservice.model;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
public class CustomResponse {

    private boolean status;
    private int errorCode;
    private Object response;
}
