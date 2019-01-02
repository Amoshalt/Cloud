package com.cloud.repositories;
import com.cloud.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    /**
     * Find a user thanks to its id
     * @param id of the user to find
     * @return user if it exists
     */
    Optional<User> findById(String id);
}
