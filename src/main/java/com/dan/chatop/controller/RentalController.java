package com.dan.chatop.controller;

import com.dan.chatop.auth.AuthenticationService;
import com.dan.chatop.dto.RentalDto;
import com.dan.chatop.model.Rental;
import com.dan.chatop.model.RentalResponse;
import com.dan.chatop.repository.UserRepository;
import com.dan.chatop.service.IRentalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/rentals")
public class RentalController {


    private final IRentalService rentalService;

    private final AuthenticationService authenticationService;

    private final UserRepository userRepository;


    @GetMapping()
    public ResponseEntity<RentalResponse> getAllRentals() {
        String userEmail = authenticationService.getAuthenticatedUserEmail();
        List<Rental> rentals = rentalService.getAllRentals();
        if (userEmail == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        log.info("get all rentals");
        return ResponseEntity.ok(new RentalResponse(rentals));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Rental> getRentalsById(@PathVariable Long id) {
        log.info("get rentals by id");
        String userEmail = authenticationService.getAuthenticatedUserEmail();

        return new ResponseEntity<>(rentalService.getRentalByUserId(id), OK);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Rental> createRental(@ModelAttribute RentalDto rentalDto) throws IOException {
//        log.info("create new rentals:" + rental.getName());

        String userEmail = authenticationService.getAuthenticatedUserEmail();
        if (userEmail == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        log.info("Adding new rental");
        return new ResponseEntity<>(rentalService.addRental(rentalDto), CREATED);
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
