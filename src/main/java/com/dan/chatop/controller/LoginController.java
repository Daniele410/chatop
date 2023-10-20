package com.dan.chatop.controller;

import com.dan.chatop.service.IUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin(origins = "*")
@RestController
public class LoginController {

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

    /*@PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserRegistrationDto user) {
        log.info("register user with email: " + user.getEmail() + " and name: " + user.getName() + " and password: " + user.getPassword() + "");
        return new ResponseEntity<>(userService.registerUser(user), CREATED );
    }*/

}
