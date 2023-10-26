package com.lms.authservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CompanyDTO {

    private String id;
    private String name;
    private String website;
    private String address;
    private String contact;
    private String password;
}
