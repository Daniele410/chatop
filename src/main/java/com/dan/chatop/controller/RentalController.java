package com.dan.chatop.controller;

import com.dan.chatop.auth.AuthenticationService;
import com.dan.chatop.dto.*;
import com.dan.chatop.model.Rental;
import com.dan.chatop.repository.RentalRepository;
import com.dan.chatop.repository.UserRepository;
import com.dan.chatop.service.IRentalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class RentalController {


    private final IRentalService rentalService;

    private final AuthenticationService authenticationService;

    private final UserRepository userRepository;

    private final RentalRepository rentalRepository;


    @GetMapping("/rentals")
    public ResponseEntity<RentalListDto> getAllRentals() {
        String userEmail = authenticationService.getAuthenticatedUserEmail();
        List<Rental> rentals = rentalService.getAllRentals();
        if (userEmail == null) {
            return ResponseEntity.badRequest().build();
        }
        log.info("get all rentals");
        return ResponseEntity.ok(new RentalListDto(rentals));
    }

    @GetMapping("/rentals/{id}")
    public ResponseEntity<RentalSimple> getRentalsById(@PathVariable Long id) {
        log.info("get rentals by id");
        String userEmail = authenticationService.getAuthenticatedUserEmail();

        if (userEmail == null) {
            return ResponseEntity.badRequest().build();
        }
        RentalSimple rentalDto = rentalService.getRentalById(id);
        return ResponseEntity.ok()
                .body(rentalDto);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/rentals")
    public ResponseEntity<MessageResponse> createRental(@ModelAttribute RentalDto rentalDto) {
        String userEmail = authenticationService.getAuthenticatedUserEmail();
        if (userEmail == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        log.info("Adding new rental");
        return new ResponseEntity<>(rentalService.createRental(rentalDto), CREATED);
    }

    @PutMapping("/rentals/{id}")
    public ResponseEntity<MessageResponse> updateRentalById(@PathVariable Long id, @ModelAttribute("rental") RentalRequestDto rentalRequestDto) {

        MessageResponse messageResponse = rentalService.updateRental(id, rentalRequestDto);

        log.info("Rental updated successfully with id:{}", id);
        return ResponseEntity.ok()
                .body(messageResponse);
    }

    @DeleteMapping("/rentals/{id}")
    public ResponseEntity<String> deleteRental(@PathVariable Long id) {
        if (!rentalService.existsById(id)) {
            return new ResponseEntity<>("Rental with id " + id + " was not present in database", NOT_FOUND);
        } else {
            rentalService.deleteRental(id);
            return new ResponseEntity<>("Rental with id " + id + " was deleted", OK);
        }
    }

}
