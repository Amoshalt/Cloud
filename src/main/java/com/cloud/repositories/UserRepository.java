package com.cloud.repositories;
import com.cloud.models.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String>{
    Optional<User> findById(String id);
    //List<User> find(Pageable pageable);
}
