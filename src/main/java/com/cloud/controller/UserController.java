package com.cloud.controller;

import com.cloud.models.User;
import com.cloud.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    @ResponseStatus(value = HttpStatus.CREATED)
    public List<User> putUsers(@Valid @RequestBody List<User> users) {
        System.out.println("\nClear DB");
        this.userRepository.deleteAll();
        System.out.println("Put " + users.size() + " users in DB");
        this.userRepository.saveAll(users);
        return getUsers();
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
    public ResponseEntity<User> getUser(@PathVariable(value = "id") String id) {
        System.out.println("\nGet user " + id);
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            userRepository.delete(user.get());
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>((User) null, HttpStatus.NOT_FOUND);
        }
    }


    /** add user
     * @return
     */
    @PostMapping("/user")
    @ResponseStatus(value = HttpStatus.CREATED)
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
    public ResponseEntity deleteUser(@PathVariable(value = "id") String id) {
        System.out.println("\nDelete user " + id);
        Optional<User> user = this.userRepository.findById(id);
        if(user.isPresent()) {
            userRepository.delete(user.get());
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
