package com.cloud.repositories;
import com.cloud.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;

public interface UserRepository extends MongoRepository<User, BigInteger>{
}
