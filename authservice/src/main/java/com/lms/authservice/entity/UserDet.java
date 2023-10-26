package com.lms.authservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDet {

    @Id
    private String email;
    private String name;
    private String password;
    private String role;
    private String company;

}
