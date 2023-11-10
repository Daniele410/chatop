package com.dan.chatop.controller;

import com.dan.chatop.dto.UserResponseDTO;
import com.dan.chatop.exception.UserNotFoundException;
import com.dan.chatop.model.User;
import com.dan.chatop.repository.UserRepository;
import com.dan.chatop.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

    private final IUserService userService;

    @Operation(summary = "Get endpoint for retrieving a user by its id", description = "Retrieve a user by its id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User retrieved successfully",
                            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class),
                                    examples = @ExampleObject(value = """
                                    {
                                      "id": 1,
                                      "name": "Owner Name",
                                      "email": "test@test.com",
                                      "createdAt": "2023-10-23T12:00:00.000+00:00",
                                      "updatedAt": "2023-10-23T12:00:00.000+00:00"
                                      }""", summary = "User retrieved successfully Example"))
                            }),
                    @ApiResponse(responseCode = "404", description = "Not Found",
                            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserNotFoundException.class),
                                    examples = @ExampleObject(value = """
                                    {
                                      "timestamp": "2023-08-31T12:00:00.000+00:00",
                                      "status": 404,
                                      "error": "Not Found",
                                      "message": "User not found with this id"
                                     }""" , summary = "User not found Example"))
                            }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized",
                            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class),
                                    examples = @ExampleObject(value = """
                                    {
                                      "timestamp": "2023-08-31T12:00:00.000+00:00",
                                      "status": 401,
                                      "error": "Unauthorized",
                                      "message": "User should be authenticated!!",
                                      "path": "/api/user/{id}"
                                    }""", summary = "Unauthorized Example"))
                            }),
                    @ApiResponse(responseCode = "403", description = "Forbidden",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                                    {
                                      "timestamp": "2023-08-31T12:00:00.000+00:00",
                                      "status": 403,
                                      "error": "Forbidden",
                                      "message": "Access Denied"
                                     }""" , summary = "Access Denied Example")))
            })
    @Parameters(@Parameter(name = "id", description = "User id", example = "1", required = true, schema = @Schema(type = "integer"), content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "1"))))
    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponseDTO> retrieveUserById(@PathVariable Long id) throws UserNotFoundException {
        Optional<User> user = Optional.ofNullable(userService.getUserById(id));
        if (user.isEmpty()) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
        UserResponseDTO meResponse =
                new UserResponseDTO(user.get().getId(), user.get().getName(), user.get().getEmail(), user.get().getCreatedAt(), user.get().getUpdatedAt());
        return ResponseEntity.ok(meResponse);
    }
}
