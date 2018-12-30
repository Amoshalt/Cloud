package com.cloud.repositories;
import com.cloud.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Long>{
    public User findById(String firstName);
}
