package com.cloud.controller;

import org.springframework.web.bind.annotation.*;

import javax.json.JsonObject;
import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class UserController {
    @GetMapping("/users")
    public JsonObject getUsers() {
        return null;
    }

    @PutMapping("/users")
    public JsonObject putUsers() {
        return null;
    }

    @DeleteMapping("/users")
    public JsonObject deleteUsers() {
        return null;
    }


    @GetMapping("/user/{id}")
    public JsonObject getUser(@PathVariable(value = "id") int id) {
        return null;
    }

    @PostMapping("/user")
    public JsonObject postUser() {
        return null;
    }

    @PutMapping("/user/{id}")
    public JsonObject putUser(@PathVariable(value = "id") int id) {
        return null;
    }

    @DeleteMapping("/user/{id}")
    public JsonObject deleteUser(@PathVariable(value = "id") int id) {
        return null;
    }
}
