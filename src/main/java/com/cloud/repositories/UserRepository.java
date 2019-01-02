package com.cloud.repositories;
import com.cloud.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String>{
    Optional<User> findById(String id);
}
