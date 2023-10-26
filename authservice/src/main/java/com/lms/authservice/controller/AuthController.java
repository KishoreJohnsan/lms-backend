package com.lms.authservice.controller;

import com.lms.authservice.exception.UserAlreadyExistsException;
import com.lms.authservice.model.CompanyDTO;
import com.lms.authservice.model.TokenResponse;
import com.lms.authservice.model.UserDTO;
import com.lms.authservice.security.JwtTokenProvider;
import com.lms.authservice.service.AuthService;
import com.lms.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/lms")
public class AuthController {

    @Autowired
    private Environment env;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping(value = "/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) throws Exception {
        return new ResponseEntity<>(authService.registerUser(userDTO), HttpStatus.CREATED);
    }

    @PostMapping(value = "/register/company")
    public ResponseEntity<?> registerCompany(@RequestBody CompanyDTO companyDTO) throws Exception {
        return new ResponseEntity<>(authService.registerCompany(companyDTO), HttpStatus.CREATED);
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserDTO request) throws Exception {


        UserDetails userDetails = userService.loadUserByUsername(request.getEmail());
        authService.authenticate(request.getEmail(), request.getPassword());
        String role = !userDetails.getAuthorities().isEmpty()
                ? userDetails.getAuthorities().stream().findFirst().get().getAuthority()
                : null;

        Map<String, Object> map = tokenProvider.generateToken(userDetails.getUsername());
        String token = (String) map.get("token");
        Date expiry = (Date) map.get("expiry");

        return new ResponseEntity<>(new TokenResponse(token, expiry, userDetails.getUsername(), role, Boolean.TRUE), HttpStatus.OK);


    }

}
