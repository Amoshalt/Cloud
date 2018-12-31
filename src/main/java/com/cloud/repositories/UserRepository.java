package com.cloud.repositories;
import com.cloud.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String>{
    public Optional<User> findById(String id);
}
