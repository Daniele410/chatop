package com.dan.chatop.controller;

import com.dan.chatop.exception.ResourceNotFoundException;
import com.dan.chatop.model.User;
import com.dan.chatop.service.IUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@CrossOrigin(origins = "*")
@RestController("/api")
public class UserController {

    private static final Logger log = LogManager.getLogger("LoginController");

    @Autowired
    IUserService userService;


    @GetMapping("/user")
    public String getUser() {
        return "Welcome, User!";
    }

    @GetMapping("/admin")
    public String getAdmin() {
        return "Welcome, Admin!";
    }




    @PostMapping("/login")
    public void login(@RequestBody User user) {


    }

    @GetMapping("/me")
    public User me() {

        return new User();
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("get all patients");
        return new ResponseEntity<>(userService.getAllUsers(), OK);
    }

    @PostMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User updateUser, @PathVariable Long id) throws ResourceNotFoundException {
        log.info("Update user whit id: " + id);
        return new ResponseEntity<>(userService.updateUser(id, updateUser), OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) throws ResourceNotFoundException {
        if (!userService.existsById(id)) {
            return new ResponseEntity<>("User with id " + id + " was not present in database", OK);
        } else {
            userService.deleteUser(id);
            return new ResponseEntity<>("User with id " + id + " was deleted", OK);
        }
    }

}
