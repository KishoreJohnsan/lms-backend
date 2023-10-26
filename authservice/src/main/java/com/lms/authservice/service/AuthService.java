package com.lms.authservice.service;

import com.lms.authservice.entity.Company;
import com.lms.authservice.entity.UserDet;
import com.lms.authservice.exception.UserAlreadyExistsException;
import com.lms.authservice.model.CompanyDTO;
import com.lms.authservice.model.UserDTO;
import com.lms.authservice.repo.CompanyRepo;
import com.lms.authservice.repo.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CompanyRepo companyRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    public void authenticate(String username, String password) throws Exception {
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", e);
        }
    }

    public boolean registerUser(UserDTO userDTO) throws Exception {

        Optional<UserDet> userOpt = userRepo.findByEmail(userDTO.getEmail());
        if(userOpt.isPresent())
            throw new UserAlreadyExistsException("User Already Exists");
        else {
            UserDet userDet = modelMapper.map(userDTO, UserDet.class);
            userDet.setRole("user");
            userDet.setPassword(passwordEncoder.encode(userDet.getPassword()));
            userRepo.save(userDet);
        }
        return true;
    }

    public boolean registerCompany(CompanyDTO companyDTO) throws UserAlreadyExistsException {

        Optional<Company> companyOpt = companyRepo.findByName(companyDTO.getName());
        if(companyOpt.isPresent())
            throw new UserAlreadyExistsException("Company Already Exists");
        else {
            Company company = modelMapper.map(companyDTO, Company.class);
            company.setRole("company");
            company.setPassword(passwordEncoder.encode(company.getPassword()));
            companyRepo.save(company);
        }
        return true;
    }
}
