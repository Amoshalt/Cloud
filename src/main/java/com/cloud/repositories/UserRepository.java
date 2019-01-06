package com.cloud.repositories;
import com.cloud.models.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    /**
     * Find a user thanks to its id
     * @param id of the user to find
     * @return user if it exists
     */
    Optional<User> findById(String id);

    @Query(value = "{ 'birthDay' : {$lte : ?0 }}")
    List<User> findOldest(Date origin, Pageable pageable);

    @Query(value = "{ 'birthDay' : {$gt : ?0, $lt : ?1}}")
    List<User> findbyExactAge(Date oldest, Date youngest, Pageable pageable);

    @Query(value = "{ 'lastName' : ?0 }")
    List<User> findByName(String name, Pageable pageable);

    @Query(value= "{'geoNear' : 'position', 'near' : [?0, ?1] }")
    List<User> findByLocationNear(double lon, double lat, Pageable pageable);
}
