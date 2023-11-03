package com.dan.chatop.controller;

import com.dan.chatop.auth.AuthenticationService;
import com.dan.chatop.dto.MessageDto;
import com.dan.chatop.dto.RentalDto;
import com.dan.chatop.dto.RentalListDto;
import com.dan.chatop.dto.RentalRequestDto;
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
    public ResponseEntity<RentalRequestDto> getRentalsById(@PathVariable Long id) {
        log.info("get rentals by id");
        String userEmail = authenticationService.getAuthenticatedUserEmail();

        if (userEmail == null) {
            return ResponseEntity.badRequest().build();
        }
        RentalRequestDto rentalDto = rentalService.getRentalByUserId(id);
        return ResponseEntity.ok()
                .body(rentalDto);
    }

//    @GetMapping("/detail/{id}")
//    public ResponseEntity<Rental> getRentalImage(@PathVariable Long id) {
//        Optional<Rental> optionalRental = rentalRepository.findById(id);
//
//        if (!optionalRental.isPresent()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.valueOf(MediaType.MULTIPART_FORM_DATA_VALUE))
//                .body(optionalRental.get());
//    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/rentals")
    public ResponseEntity<Rental> createRental(@ModelAttribute RentalDto rentalDto) {
        String userEmail = authenticationService.getAuthenticatedUserEmail();
        if (userEmail == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        log.info("Adding new rental");
        return new ResponseEntity<>(rentalService.createRental(rentalDto), CREATED);
    }

    //    @PutMapping("/rentals/{id}")
//    public ResponseEntity<Rental> updateRental(@PathVariable Long id, @RequestBody Rental rental) {
//        return new ResponseEntity<>(rentalService.updateRental(rental), ACCEPTED);
//    }
    @PutMapping("/rentals/{id}")
    public ResponseEntity<RentalRequestDto> updateRentalById(@PathVariable Long id, @ModelAttribute("rental") RentalRequestDto rentalRequestDto) {

        RentalRequestDto messageResponse = rentalService.updateRental(id, rentalRequestDto);

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
