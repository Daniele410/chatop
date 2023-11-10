package com.dan.chatop.controller;

import com.dan.chatop.auth.AuthenticationService;
import com.dan.chatop.dto.*;
import com.dan.chatop.exception.InvalidImageFormatException;
import com.dan.chatop.exception.RentalNotFondException;
import com.dan.chatop.exception.ResourceNotFoundException;
import com.dan.chatop.model.Rental;
import com.dan.chatop.service.IRentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@CrossOrigin(origins = "*")
@Slf4j
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class RentalController {


    private final IRentalService rentalService;

    private final AuthenticationService authenticationService;

    @Operation(summary = "Get all rentals", description = "Get all rentals",
            responses = {
                    @ApiResponse(responseCode = "200", description = "All Rentals retrieved successfully",
                            content ={@Content(mediaType = "application/json", schema = @Schema(implementation = RentalListDto.class),
                                    examples = @ExampleObject(value = """
                                    {
                                    "rentals": [
                                        {
                                            "id": 1,
                                            "name": "Appartement 1",
                                            "surface": 100,
                                            "price": 900,
                                            "picture": "https://chatop.com/images/1",
                                            "description": "Appartement 1"
                                        },
                                        {
                                            "id": 2,
                                            "name": "Appartement 2",
                                            "surface": 200,
                                            "price": 2000,
                                            "picture": "https://chatop.com/images/2",
                                            "description": "Appartement 2"
                                        }
                                    ]
                                    }
                                    """, summary = "Rentals retrieved successfully"))
                            }),
                    @ApiResponse(responseCode = "404", description = "Rentals not found",
                            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RentalNotFondException.class),
                                    examples = @ExampleObject(value = """
                                    {
                                      "timestamp": "2023-08-31T12:00:00.000+00:00",
                                      "status": 404,
                                      "error": "Not Found",
                                      "message": "Rentals not found",
                                      "path": "/api/rentals"
                                    }""", summary = "Rentals not found"))
                            }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized",
                            content ={@Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class),
                                    examples = @ExampleObject(value = """
                                    {
                                      "timestamp": "2023-08-31T12:00:00.000+00:00",
                                      "status": 401,
                                      "error": "Unauthorized",
                                      "message": "Unauthorized User, user not logged in",
                                      "path": "/api/rentals"
                                    }""", summary = "Unauthorized User"))}
                    ),
            })
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

    @Operation(summary = "Get a rental by its id", description = "Get a rental by its id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Rental retrieved successfully",
                            content ={@Content(mediaType = "application/json", schema = @Schema(implementation = RentalSimple.class),
                                    examples = @ExampleObject(value = """
                                    {
                                      "id": 1,
                                      "name": "Appartement 1",
                                      "surface": 100,
                                      "price": 1000,
                                      "picture": "https://chatop.com/images/1",
                                      "description": "Rent 1"
                                    }
                                    """, summary = "Rental retrieved successfully"))
                            }),
                    @ApiResponse(responseCode = "404", description = "Rental not found",
                            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RentalNotFondException.class),
                                    examples = @ExampleObject(value = """
                                    {
                                      "timestamp": "2023-08-31T12:00:00.000+00:00",
                                      "status": 404,
                                      "error": "Not Found",
                                      "message": "Rental not found",
                                      "path": "/api/rentals/1"
                                    }""", summary = "Rental not found"))
                            }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized",
                            content ={@Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class),
                                    examples = @ExampleObject(value = """
                                    {
                                      "timestamp": "2023-08-31T12:00:00.000+00:00",
                                      "status": 401,
                                      "error": "Unauthorized",
                                      "message": "Unauthorized User, user not logged in",
                                      "path": "/api/rentals/1"
                                    }""", summary = "Unauthorized User"))}
                    ),
            })
    @Parameters(@Parameter(name = "id",
            description = "Id of Rental to be searched", example = "1", required = true,
            schema = @Schema(type = "integer"), content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "1"))))
    @SecurityRequirement(name = "bearerAuth")
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

    @Operation(summary = "Create a new rental", description = "Create a new rental, image is required",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Rental created successfully",
                            content ={ @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class),
                                    examples = @ExampleObject(value = """
                                    {
                                        "message": "Rental created successfully"
                                        }
                                        """, summary = "Rental created successfully"))
                            }),
                    @ApiResponse(responseCode = "400", description = "Fields are missing",
                            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class),
                                    examples = @ExampleObject(value = """
                                    {
                                        "timestamp": "2023-10-31T12:00:00.000+00:00",
                                        "status": 400,
                                        "error": "Bad Request",
                                        "message": "Fields cannot be null or empty",
                                        "path": "/api/rentals"
                                    }""", summary = "Fields are missing"))
                            }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized",
                            content ={ @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class),
                                    examples = @ExampleObject(value = """
                                    {
                                      "timestamp": "2023-10-31T12:00:00.000+00:00",
                                      "status": 401,
                                      "error": "Unauthorized",
                                      "message": "Unauthorized User, user not logged in",
                                      "path": "/api/rentals"
                                    }""", summary = "Unauthorized User"))
                            }),
                    @ApiResponse(responseCode = "403", description = "Access Denied",
                            content ={@Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class),
                                    examples = @ExampleObject(value = """
                                    {
                                      "timestamp": "2023-10-31T12:00:00.000+00:00",
                                      "status": 403,
                                      "error": "Forbidden",
                                      "message": "Access Denied",
                                      "path": "/api/rentals"
                                    }""", summary = "Access Denied"))
                            }),
                    @ApiResponse(responseCode = "405", description = "Invalid image format",
                            content ={@Content(mediaType = "application/json", schema = @Schema(implementation = InvalidImageFormatException.class),
                                    examples = @ExampleObject(value = """
                                    {
                                      "timestamp": "2023-10-31T12:00:00.000+00:00",
                                      "status": 405,
                                      "error": "Invalid image format",
                                      "message": "Image format must be jpg, jpeg or png",
                                      "path": "/api/rentals"
                                    }""", summary = "Image format must be jpg, jpeg or png"))
                            })
            })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Rental object that needs to be created", required = true,
            content = @Content(mediaType = "multipart/form-data", schema = @Schema(implementation = RentalDto.class))
    )
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/rentals")
    public ResponseEntity<MessageResponse> createRental(@ModelAttribute RentalDto rentalDto) {
        String userEmail = authenticationService.getAuthenticatedUserEmail();
        if (userEmail == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        log.info("Adding new rental");
        return new ResponseEntity<>(rentalService.createRental(rentalDto), CREATED);
    }

    @Operation(summary = "Update Rental by ID", description = "Update a rental by its ID.")
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/rentals/{id}")
    public ResponseEntity<MessageResponse> updateRentalById(@PathVariable Long id, @ModelAttribute("rental") RentalRequestDto rentalRequestDto) {

        MessageResponse messageResponse = rentalService.updateRental(id, rentalRequestDto);

        log.info("Rental updated successfully with id:{}", id);
        return ResponseEntity.ok()
                .body(messageResponse);
    }
    @Operation(summary = "Delete Rental by ID", description = "Delete a rental by its ID.")
    @SecurityRequirement(name = "bearerAuth")
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
