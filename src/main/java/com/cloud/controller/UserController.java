package com.cloud.controller;

import com.cloud.models.User;
import com.cloud.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

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
        List<User> users = this.userRepository.findAll();
        return users;
    }

    /** replace users by users
     * @return
     */
    @PutMapping("/user")
    public void putUsers(@Valid @RequestBody List<User> users) {
        this.userRepository.deleteAll();
        this.userRepository.saveAll(users);
    }

    /** delete all users
     * @return
     */
    @DeleteMapping("/user")
    public void deleteUsers() {
        this.userRepository.deleteAll();
    }


    /** get user by id
     * @param id
     * @return User
     */
    @GetMapping("/user/{id}")
    public Optional<User> getUser(@PathVariable(value = "id") BigInteger id) {
        return this.userRepository.findById(id);
    }


    /** add user
     * @return
     */
    @PostMapping("/user")
    public @Valid User postUser(@Valid @RequestBody User user) {
        return this.userRepository.insert(user);
    }

    /** update user by id
     * @param id
     * @return
     */
    @PutMapping("/user/{id}")
    public void putUser(@PathVariable(value = "id") int id, @Valid @RequestBody User user) {
        this.userRepository.save(user);
    }

    /** delete user by id
     * @param id
     * @return
     */
    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable(value = "id") BigInteger id) {
        this.userRepository.findById(id).ifPresent(user -> this.userRepository.delete(user));
    }
}
