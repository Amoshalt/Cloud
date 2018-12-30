package com.cloud.controller;

import com.cloud.models.User;
import com.cloud.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.json.JsonObject;
import net.minidev.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserRepository repository;

    /** get all users
     * @return
     */
    @GetMapping("/user")
    public List<User> getUsers() {
        return repository.findAll();
    }

    /** replace users by users
     * @return
     */
    @PutMapping("/user")
    public JsonObject putUsers(@RequestBody User[] users) {
        repository.deleteAll();
        repository.insert(Arrays.asList(users));
        return null;
    }

    /** delete all users
     * @return
     */
    @DeleteMapping("/user")
    public JsonObject deleteUsers() {
        repository.deleteAll();
        return null;
    }


    /** get user by id
     * @param id
     * @return User
     */
    @GetMapping("/user/{id}")
    public User getUser(@PathVariable(value = "id") String id) {
        return null;
    }


    /** add user
     * @return
     */
    @PostMapping("/user")
    public JsonObject postUser() {
        return null;
    }

    /** update user by id
     * @param id
     * @return
     */
    @PutMapping("/user/{id}")
    public JsonObject putUser(@PathVariable(value = "id") String id, @RequestBody User user) {
        repository.save(user);
        return null;
    }

    /** delete user by id
     * @param id
     * @return
     */
    @DeleteMapping("/user/{id}")
    public JsonObject deleteUser(@PathVariable(value = "id") String id) {
        //TODO
        return null;
    }
}
