package com.lms.authservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {

    private String email;
    private String name;
    private String password;
    private String role;
    private String company;
}
