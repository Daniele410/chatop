package com.dan.chatop.auth;

import com.dan.chatop.exception.DataEntryError;
import com.dan.chatop.exception.ResourceNotFoundException;
import com.dan.chatop.model.User;
import com.dan.chatop.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

@CrossOrigin(origins = "*")
@Slf4j
@Tag(name = "Login API", description = "Login API for ChaTop application")
@SecurityRequirement(name = "noAuth")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    @Operation(summary = "User Authentication, User Sign In", description = "Authenticate the user and return a JWT token if the user is valid.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User registered successfully",
                            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponse.class),
                                    examples = @ExampleObject(value = """
                                            {
                                            "token" : "Valid JWT token"
                                            }
                                            """, summary = "User Authentication Example"))
                            }),

                    @ApiResponse(responseCode = "401", description = "Bad Credentials",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                                    {
                                      "timestamp": "2023-10-31T12:00:00.000+00:00",
                                      "status": 403,
                                      "error": "Forbidden",
                                      "message": "Fields should not be empty!",
                                      "path": "/api/auth/login"
                                    }""", summary = "Bad Credentials Example"))),

                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class),
                                    examples = @ExampleObject(value = """
                                            {
                                              "timestamp": "2023-10-31T12:00:00.000+00:00",
                                              "status": 400,
                                              "error": "Bad Request",
                                              "message": "Email already exists",
                                              "path": "/api/auth/login"
                                            }""", summary = "Bad Request Example"))
                            })
            })
    @Parameters(@Parameter(name = "AuthRequest", description = "User credentials for authentication"))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
            {
              "name": "test_user",
              "email": "test@test.com",
              "password": "test!31"
            }""", summary = "User Authentication Example"))
    )
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) throws Exception {

        if(StringUtils.isAnyBlank(request.getName(), request.getEmail(), request.getPassword())) {
            log.error("Fields should not be empty!");
            return new ResponseEntity<>(new ErrorMessage("Fields should not be empty!"), HttpStatus.FORBIDDEN);
        }
        Optional<User> userEmail = userRepository.findByEmail(request.getEmail());
        if (userEmail.isPresent()) {
            log.error("Email already exists");
            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        }
        log.info("User registered successfully");
        return new ResponseEntity<>(authenticationService.register(request), OK);
    }

    @SneakyThrows
    @Operation(summary = "User Authentication, User Sign In", description = "Authenticate the user and return a JWT token if the user is valid.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User registered successfully",
                            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationRequest.class),
                                    examples = @ExampleObject(value = """
                                    {
                                    "token" : "Valid JWT token"
                                    }
                                    """, summary = "User Authentication Example"))
                            }),

                    @ApiResponse(responseCode = "401", description = "Bad Credentials",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                                    {
                                      "timestamp": "2023-08-31T12:00:00.000+00:00",
                                      "status": 401,
                                      "error": "Unauthorized",
                                      "message": "Email Not present in Database",
                                      "path": "/api/auth/login"
                                    }""", summary = "Bad Credentials Example"))),

                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class),
                                    examples = @ExampleObject(value = """
                                    {
                                      "timestamp": "2023-08-31T12:00:00.000+00:00",
                                      "status": 400,
                                      "error": "Bad Request",
                                      "message": "Incorrect credentials or Fields should not be empty!",
                                      "path": "/api/auth/login"
                                    }""", summary = "Bad Request Example"))
                            })
            })
    @Parameters(@Parameter(name = "AuthRequest", description = "User credentials for authentication"))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
            {
              "email": "test@test.com",
              "password": "test!31"
            }""", summary = "User login Authentication Example"))
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
        Optional<User> userEmail = userRepository.findByEmail(request.getEmail());
        if (!userEmail.isPresent()) {
            if (request.getEmail() == null || request.getPassword() == null || request.getEmail().isEmpty() || request.getPassword().isEmpty()) {
                log.error("Fields should not be empty!");
                return new ResponseEntity<>(new ErrorMessage("Incorrect credentials or Fields should not be empty!"), HttpStatus.BAD_REQUEST);
            }
            log.error("Email Not present in Database");
            return new ResponseEntity<>("Email Not present in Database", HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(authenticationService.authenticate(request), OK);
    }

}
