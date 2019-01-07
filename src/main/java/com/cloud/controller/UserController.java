package com.cloud.controller;

import com.cloud.models.User;
import com.cloud.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    private static final Pageable defaultPage = PageRequest.of(0, 100).first();

    /** get all users
     * @return List that contains all the users in the DB
     */
    @GetMapping("/user")
    public List<User> getUsersPage(@RequestParam Map<String, String> params) {
        Pageable page = null;
        if(params != null && params.containsKey("page")) {
            page = PageRequest.of(
                    Integer.parseInt(params.get("page")),
                    100
            );
        }
        return userRepository.findAll(page != null ? page : defaultPage).getContent();
    }
/*
    @GetMapping("/user")
    public List<User> getUsers() {
        return userRepository.findAll(defaultPage).getContent();
    }
*/
    /** replace users by users
     * @return List that contains all the users in the DB
     */
    @PutMapping("/user")
    @ResponseStatus(value = HttpStatus.CREATED)
    public List<User> putUsers(@Valid @RequestBody List<User> users) {
        
        userRepository.deleteAll();
        
        userRepository.saveAll(users);
        return getUsersPage(null);
    }

    /** delete all users
     * @return Empty array
     */
    @DeleteMapping("/user")
    public void deleteUsers() {
        
        userRepository.deleteAll();
    }


    /** get user by id
     * @param id of the user to return
     * @return User with the matching id if it exists
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable(value = "id") String id) {
        
        Optional<User> user = userRepository.findById(id);
        return user.map(user1 -> new ResponseEntity<>(user1, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    /** add user
     * @param user to add
     * @return The added user
     */
    @PostMapping("/user")
    @ResponseStatus(value = HttpStatus.CREATED)
    public @Valid User postUser(@Valid @RequestBody User user) {
        
        return userRepository.save(user);
    }

    /** update user by id
     * @param id of the user to update
     * @param user containing the new data
     * @return nothing
     */
    @PutMapping("/user/{id}")
    public JsonObject putUser(@PathVariable(value = "id") String id, @RequestBody User user) {
        Optional<User> u = userRepository.findById(id);
        if(u.isPresent()) {
            user.setId(id);
            userRepository.save(user);
        }
        return null;
    }

    /** delete user by id
     * @param id of the user to delete
     * @return nothing
     */
    @DeleteMapping("/user/{id}")
    public ResponseEntity deleteUser(@PathVariable(value = "id") String id) {
        
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
        ResponseEntity response = new ResponseEntity(HttpStatus.BAD_REQUEST);
        
        if(params != null) {
            int age;
            Pageable page = defaultPage;
            if(params.containsKey("page")) {
                int ipage = Integer.parseInt(params.get("page").get(0));
                if(ipage > 0)
                    page = PageRequest.of(ipage, 100);

            }
            if(params.containsKey("gt")) {
                age = Integer.parseInt(params.get("gt").get(0));
                if(age >= 0) {
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.YEAR, -1 * age);
                    Date ago = cal.getTime();
                    List<User> users = userRepository.findOldest(ago, page);
                    response = new ResponseEntity<>(users, HttpStatus.OK);
                }
            } else if(params.containsKey("eq")) {
                age = Integer.parseInt(params.get("eq").get(0));
                if(age >= 0) {
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.YEAR, -1 * age);
                    Date oldest = cal.getTime();
                    cal.add(Calendar.YEAR, 1);
                    Date youngest = cal.getTime();
                    List<User> users = userRepository.findbyExactAge(oldest, youngest, page);
                    response = new ResponseEntity<>(users, HttpStatus.OK);
                }
            }
        }
        return response;
    }

    @GetMapping("/user/search")
    public ResponseEntity getUserFromText(@RequestParam MultiValueMap<String, String> params) {
        ResponseEntity response = new ResponseEntity(HttpStatus.BAD_REQUEST);
        
        if(params != null) {
            Pageable page = defaultPage;
            if(params.containsKey("page")) {
                int ipage = Integer.parseInt(params.get("page").get(0));
                if(ipage > 0)
                    page = PageRequest.of(ipage, 100);
            }
            if(params.containsKey("name") || params.containsKey("term")) {
                String name;
                if (params.containsKey("name"))
                    name = params.get("name").get(0);
                else name = params.get("term").get(0);
                if (!name.equals("")) {
                    List<User> users = userRepository.findByName(name, page);
                    response = new ResponseEntity<>(users, HttpStatus.OK);
                }
            }
        }
        return response;
    }

    @GetMapping("/user/nearest")
    public ResponseEntity getUserFromPosition(@RequestParam MultiValueMap<String, String> params) {
        ResponseEntity response = new ResponseEntity(HttpStatus.BAD_REQUEST);
        
        if(params != null) {
            Pageable page = PageRequest.of(0, 10);
            if(params.containsKey("page")) {
                int ipage = Integer.parseInt(params.get("page").get(0));
                if(ipage > 0)
                    page = PageRequest.of(ipage, 10);
            }
            if(params.containsKey("lat") && params.containsKey("lon")) {
                double lat, lon;
                lat = Double.parseDouble(params.get("lat").get(0));
                lon = Double.parseDouble(params.get("lon").get(0));
                if((lat >= -90)
                        && (lat <= 90)
                        && (lon >= -180)
                        && (lon <= 180)) {
                    List<User> users = userRepository.findByLocationNear(lat, lon, page);
                    response = new ResponseEntity<>(users, HttpStatus.OK);
                }
            }
        }
        return response;
    }
}
