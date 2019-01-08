package com.cloud.repositories;
import com.cloud.models.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Override
    @Caching(evict = {
            @CacheEvict(value="list", allEntries=true),
            @CacheEvict(value="page", allEntries=true) })
    <S extends User> S save(S s);

    @Override
    @Caching(evict = {
            @CacheEvict(value="list", allEntries=true),
            @CacheEvict(value="page", allEntries=true) })
    <S extends User> List<S> saveAll(Iterable<S> iterable);

    @Override
    @Caching(evict = {
            @CacheEvict(value="list", allEntries=true),
            @CacheEvict(value="page", allEntries=true) })
    void delete(User user);

    @Override
    @Caching(evict = {
            @CacheEvict(value="list", allEntries=true),
            @CacheEvict(value="page", allEntries=true) })
    void deleteAll(Iterable<? extends User> iterable);

    @Override
    @Caching(evict = {
            @CacheEvict(value="list", allEntries=true),
            @CacheEvict(value="page", allEntries=true) })
    void deleteAll();

    //@Cacheable (value = "page", key = "#pageable")
    Page<User> findAll(Pageable pageable);

    @Query(value = "{ 'birthDay' : {$lte : ?0 }}")
    List<User> findOldest(Date origin, Pageable pageable);

    @Query(value = "{ 'birthDay' : {$gt : ?0, $lt : ?1}}")
    List<User> findbyExactAge(Date oldest, Date youngest, Pageable pageable);

    @Query(value = "{ $or: [{'lastName' : ?0 }, {'firstName' : ?0 }]}")
    List<User> findByName(String name, Pageable pageable);

    @Query(value= "{ 'position' : {$near : [?1, ?0]} }")
    List<User> findByLocationNear(double lat, double lon, Pageable pageable);
}
