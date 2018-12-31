package com.cloud.controller;

import com.cloud.models.User;
import com.cloud.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.*;
import javax.json.JsonObject;

import java.util.List;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserRepository userRepository;


    /** get all users
     * @return
     */
    @GetMapping("/user")
    public List<User> getUsers() {
        System.out.println("\nGet all users");
        List<User> users = this.userRepository.findAll();
        System.out.println("Nb of users : " + users.size());
        return users;
    }

    /** replace users by users
     * @return
     */
    @PutMapping("/user")
    public void putUsers(@Valid @RequestBody List<User> users) {
        System.out.println("\nClear DB");
        this.userRepository.deleteAll();
        System.out.println("Put " + users.size() + " users in DB");
        this.userRepository.saveAll(users);
    }

    /** delete all users
     * @return
     */
    @DeleteMapping("/user")
    public void deleteUsers() {
        System.out.println("\nClear DB");
        this.userRepository.deleteAll();
    }


    /** get user by id
     * @param id
     * @return User
     */
    @GetMapping("/user/{id}")
    public Optional<User> getUser(@PathVariable(value = "id") String id) {
        System.out.println("\nGet user " + id);
        return userRepository.findById(id);
    }


    /** add user
     * @return
     */
    @PostMapping("/user")
    public @Valid User postUser(@Valid @RequestBody User user) {
        System.out.println("\nPost ???");
        return this.userRepository.insert(user);
    }

    /** update user by id
     * @param id
     * @return
     */
    @PutMapping("/user/{id}")
    public JsonObject putUser(@PathVariable(value = "id") String id, @RequestBody User user) {
        System.out.println("\nPut user " + id);
        userRepository.save(user);
        return null;
    }

    /** delete user by id
     * @param id
     * @return
     */
    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable(value = "id") String id) {
        System.out.println("\nDelete user " + id);
        this.userRepository.findById(id).ifPresent(user -> this.userRepository.delete(user));
    }

    private BigInteger parseID(String id) {
        return BigInteger.valueOf(Long.parseLong(id));
    }
}
