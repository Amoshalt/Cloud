package com.cloud.controller;

import com.cloud.models.User;
import org.springframework.web.bind.annotation.*;

import javax.json.JsonObject;
import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class UserController {


    /** get all users
     * @return
     */
    @GetMapping("/users")
    public User[] getUsers() {
        return null;
    }

    /** replace users by users
     * @return
     */
    @PutMapping("/users")
    public JsonObject putUsers() {
        return null;
    }

    /** delete all users
     * @return
     */
    @DeleteMapping("/users")
    public JsonObject deleteUsers() {
        return null;
    }


    /** get user by id
     * @param id
     * @return User
     */
    @GetMapping("/user/{id}")
    public User getUser(@PathVariable(value = "id") int id) {
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
    public JsonObject putUser(@PathVariable(value = "id") int id) {
        return null;
    }

    /** delete user by id
     * @param id
     * @return
     */
    @DeleteMapping("/user/{id}")
    public JsonObject deleteUser(@PathVariable(value = "id") int id) {
        return null;
    }
}
