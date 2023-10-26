package com.lms.authservice.repo;

import com.lms.authservice.entity.UserDet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends MongoRepository<UserDet, String> {

        Optional<UserDet> findByEmail(String email);

}
