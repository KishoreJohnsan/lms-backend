package com.lms.authservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class TokenResponse {

    private String token;
    private Date expiry;
    private String user;
    private String role;
    private Boolean status;
}
