package com.lms.authservice.service;

import com.lms.authservice.entity.UserDet;
import com.lms.authservice.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserDet> userOpt = repo.findByEmail(email);
        if(userOpt.isPresent()){
            UserDet userDet = userOpt.get();
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_".concat(userDet.getRole().toUpperCase()));
            return new User(userDet.getEmail(), userDet.getPassword(), Arrays.asList(authority));
        }
        else throw new UsernameNotFoundException("Username Not Found");

    }
}
