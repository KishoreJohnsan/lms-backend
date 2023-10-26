package com.lms.courseservice.repo;

import com.lms.courseservice.entity.Course;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepo extends MongoRepository<Course, String> {

    List<Course> findByTechnologyLikeIgnoreCase(String technology);

    List<Course> findByNameLikeIgnoreCase(String name);

    @Query("{'technology' : { $regex: /^?0/, $options: 'i' },  'duration' : { $gte: ?1, $lte: ?2 } }")
    List<Course> findByTechnologyAndDurationLikeIgnoreCase(String technology, String durationFrom, String durationTo);

    Optional<Course> findByNameAndTechnologyAndDuration(String name, String technology, String duration);


}
