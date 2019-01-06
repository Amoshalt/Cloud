package com.cloud.controller;

import com.cloud.models.User;
import com.cloud.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
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
     * @return List that contains all the users in the DB
     */
    @GetMapping("/user")
    public List<User> getUsers(@RequestParam MultiValueMap<String, String> params) {
        List<User> users;
        int ipage = 0;
        if(params != null && params.containsKey("page")) {

            String arg = params.get("page").get(0);
            ipage = Integer.parseInt(arg);
        }
        System.out.println("\nGet users from " + (ipage*100) + " to " + ((ipage*100) + 99));
        Page<User> pusers = this.userRepository.findAll(PageRequest.of(ipage, 100));
        users = pusers.getContent();
        System.out.println("Nb of users : " + users.size());
        return users;
    }

    /** replace users by users
     * @return List that contains all the users in the DB
     */
    @PutMapping("/user")
    @ResponseStatus(value = HttpStatus.CREATED)
    public List<User> putUsers(@Valid @RequestBody List<User> users) {
        System.out.println("\nClear DB");
        this.userRepository.deleteAll();
        System.out.println("Put " + users.size() + " users in DB");
        this.userRepository.saveAll(users);
        return getUsers(null);
    }

    /** delete all users
     * @return Empty array
     */
    @DeleteMapping("/user")
    public void deleteUsers() {
        System.out.println("\nClear DB");
        this.userRepository.deleteAll();
    }


    /** get user by id
     * @param id of the user to return
     * @return User with the matching id if it exists
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable(value = "id") String id) {
        System.out.println("\nGet user " + id);
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            userRepository.delete(user.get());
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    /** add user
     * @param user to add
     * @return The added user
     */
    @PostMapping("/user")
    @ResponseStatus(value = HttpStatus.CREATED)
    public @Valid User postUser(@Valid @RequestBody User user) {
        System.out.println("\nPost user");
        return this.userRepository.insert(user);
    }

    /** update user by id
     * @param id of the user to update
     * @param user containing the new data
     * @return nothing
     */
    @PutMapping("/user/{id}")
    public JsonObject putUser(@PathVariable(value = "id") String id, @RequestBody User user) {
        System.out.println("\nPut user " + id);
        userRepository.save(user);
        return null;
    }

    /** delete user by id
     * @param id of the user to delete
     * @return nothing
     */
    @DeleteMapping("/user/{id}")
    public ResponseEntity deleteUser(@PathVariable(value = "id") String id) {
        System.out.println("\nDelete user " + id);
        Optional<User> user = this.userRepository.findById(id);
        if(user.isPresent()) {
            userRepository.delete(user.get());
            return new ResponseEntity(null, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/age")
    public ResponseEntity getUserFromAge(@RequestParam MultiValueMap<String, String> params) {
        ResponseEntity response = null;
        if(params != null) {
            int age;
            int page = 0;
            if(params.containsKey("page")) {
                page = Integer.parseInt(params.get("page").get(0));
                if(page < 0)
                    page = 0;
            }
            if(params.containsKey("gt")) {
                age = Integer.parseInt(params.get("gt").get(0));
                if(age < 0) {
                    response = new ResponseEntity(HttpStatus.BAD_REQUEST);
                } else {
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.YEAR, -1 * age);
                    Date ago = cal.getTime();
                    List<User> users = userRepository.findOldest(ago, PageRequest.of(page, 100));
                    response = new ResponseEntity<>(users, HttpStatus.OK);
                }
            } else if(params.containsKey("eq")) {
                age = Integer.parseInt(params.get("eq").get(0));
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.YEAR, -1*age);
                Date oldest = cal.getTime();
                cal.add(Calendar.YEAR, 1);
                Date youngest = cal.getTime();
                List<User> users = userRepository.findbyExactAge(oldest, youngest, PageRequest.of(page, 100));
                response = new ResponseEntity<>(users, HttpStatus.OK);
            }
        }
        return response;
    }

    @GetMapping("/user/search")
    public ResponseEntity getUserFromText(@RequestParam MultiValueMap<String, String> params) {
        ResponseEntity response = null;
        if(params != null) {
            int page = 0;
            if(params.containsKey("page")) {
                page = Integer.parseInt(params.get("page").get(0));
                if(page < 0)
                    page = 0;
            }
            if(params.containsKey("name") || params.containsKey("term")) {
                String name;
                if (params.containsKey("name"))
                    name = params.get("name").get(0);
                else name = params.get("term").get(0);
                if (name.equals("")) {
                    response = new ResponseEntity(HttpStatus.BAD_REQUEST);
                } else {
                    List<User> users = userRepository.findByName(name, PageRequest.of(page, 100));
                    response = new ResponseEntity<>(users, HttpStatus.OK);
                }
            }
        }
        return response;
    }

    @GetMapping("/user/nearest")
    public ResponseEntity getUserFromPosition(@RequestParam MultiValueMap<String, String> params) {
        ResponseEntity response = null;
        if(params != null) {
            int page = 0;
            if(params.containsKey("page")) {
                page = Integer.parseInt(params.get("page").get(0));
                if(page < 0)
                    page = 0;
            }
            if(params.containsKey("lat") && params.containsKey("lon")) {
                double lat, lon;
                lat = Double.parseDouble(params.get("lat").get(0));
                lon = Double.parseDouble(params.get("lon").get(0));
                List<User> users = userRepository.findByLocationNear(lon, lat, PageRequest.of(page, 10));
                response = new ResponseEntity<>(users, HttpStatus.OK);
            }
        }
        return response;
    }
}
