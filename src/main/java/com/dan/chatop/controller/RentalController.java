package com.dan.chatop.controller;

import com.dan.chatop.model.Rental;
import com.dan.chatop.service.IRentalService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private static final Logger log = LogManager.getLogger("RentalController");

    @Autowired
    IRentalService rentalService;


    @GetMapping()
    public ResponseEntity<List<Rental>> getAllRentals() {
        log.info("get all rentals");
        return new ResponseEntity<>(rentalService.getAllRentals(), OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rental> getRentalsById(@PathVariable Long id) {
        log.info("get rentals by id");
        return new ResponseEntity<>(rentalService.getRentalByUserId(id), OK);
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Rental> createRental(Rental rental) {
        log.info("create new rentals:" + rental.getName());
        return new ResponseEntity<>(rentalService.addRental(rental), CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rental> updateRental(@PathVariable Long id, @RequestBody Rental rental) {
        return new ResponseEntity<>(rentalService.updateRental(rental), ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRental(@PathVariable Long id) {
        if (!rentalService.existsById(id)) {
            return new ResponseEntity<>("Rental with id " + id + " was not present in database", NOT_FOUND);
        } else {
            rentalService.deleteRental(id);
            return new ResponseEntity<>("Rental with id " + id + " was deleted", OK);
        }
    }

}
