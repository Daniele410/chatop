package com.dan.chatop.controller;

import com.dan.chatop.model.User;
import com.dan.chatop.service.IUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;


@RestController("/api")
public class UserController {

    private static final Logger log = LogManager.getLogger("UserController");

    @Autowired
    IUserService userService;


    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllPatients() {
        log.info("get all patients");
        return new ResponseEntity<>(userService.getAllUsers(), OK);
    }
}
