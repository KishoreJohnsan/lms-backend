package com.lms.authservice.repo;

import com.lms.authservice.entity.Company;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepo extends MongoRepository<Company, String> {
    Optional<Company> findByName(String name);
}
