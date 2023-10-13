package com.dan.chatop.controller;

import com.dan.chatop.model.Rental;
import com.dan.chatop.service.IRentalService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;


@RestController(value = "api")
public class RentalController {

    private static final Logger log = LogManager.getLogger("RentalController");

    @Autowired
    IRentalService rentalService;

    @GetMapping("/rentals")
    public ResponseEntity<List<Rental>> getAllRentals() {
        log.info("get all rentals");
        return new ResponseEntity<>(rentalService.getAllRentals(), OK);
    }

}
