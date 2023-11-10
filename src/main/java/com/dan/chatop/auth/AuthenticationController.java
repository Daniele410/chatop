package com.dan.chatop.auth;

import com.dan.chatop.dto.UserResponseDTO;
import com.dan.chatop.exception.ResourceNotFoundException;
import com.dan.chatop.exception.UserNotFoundException;
import com.dan.chatop.model.User;
import com.dan.chatop.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

@CrossOrigin(origins = "*")
@Slf4j
@Tag(name = "Login API", description = "Login API for ChâTop application")
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
                                      "timestamp": "2023-08-31T12:00:00.000+00:00",
                                      "status": 401,
                                      "error": "Unauthorized",
                                      "message": "Incorrect credentials !!",
                                      "path": "/api/auth/login"
                                    }""", summary = "Bad Credentials Example"))),

                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class),
                                    examples = @ExampleObject(value = """
                                            {
                                              "timestamp": "2023-08-31T12:00:00.000+00:00",
                                              "status": 400,
                                              "error": "Bad Request",
                                              "message": "Fields should not be empty!!",
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
    public ResponseEntity<Object> register(@RequestBody RegisterRequest request) throws Exception {
        Optional<User> userEmail = userRepository.findByEmail(request.getEmail());
        if (userEmail.isPresent()) {

            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(authenticationService.register(request), OK);
    }

    @Operation(summary = "User Authentication, User Sign In", description = "Authenticate the user and return a JWT token if the user is valid.")
    @ApiResponse(responseCode = "200", description = "User authenticated successfully",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponse.class),
                    examples = @ExampleObject(value = """
                            {
                            "token" : "Valid JWT token"
                            }
                            """, summary = "User Authentication Example"))
            })
    @ApiResponse(responseCode = "401", description = "Bad Credentials",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                      "timestamp": "2023-08-31T12:00:00.000+00:00",
                      "status": 401,
                      "error": "Unauthorized",
                      "message": "Incorrect credentials !!",
                      "path": "/api/auth/login"
                    }""", summary = "Bad Credentials Example")))
    @Parameters(@Parameter(name = "AuthRequest", description = "User credentials for authentication"))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
            {
              "email": "test@test.com",
              "password": "test!31"
            }""", summary = "User Authentication Example"))
    )
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        return new ResponseEntity<>(authenticationService.authenticate(request), OK);
    }

    @Operation(summary = "Get Current User", description = "Retrieve information about the currently authenticated user.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User retrieved successfully",
                            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class),
                                    examples = @ExampleObject(value = """
                                    {
                                      "id": 1,
                                      "name": "test Test",
                                      "email": "test@test.com",
                                      "createdAt": "2023-10-23T12:00:00.000+00:00",
                                      "updatedAt": "2023-10-23T12:00:00.000+00:00"
                                      }""", summary = "User retrieved successfully Example"))
                            }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized",
                            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class),
                                    examples = @ExampleObject(value = """
                                    {
                                      "timestamp": "2023-08-31T12:00:00.000+00:00",
                                      "status": 401,
                                      "error": "Unauthorized",
                                      "message": "User should be authenticated!!",
                                      "path": "/api/auth/me"
                                    }""", summary = "Unauthorized Example"))
                            }),
                    @ApiResponse(responseCode = "404", description = "Not Found",
                            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserNotFoundException.class),
                                    examples = @ExampleObject(value = """
                                    {
                                      "timestamp": "2023-08-31T12:00:00.000+00:00",
                                      "status": 404,
                                      "error": "Not Found",
                                      "message": "User not found with this email"
                                     }""" , summary = "User not found Example"))
                            }),
                    @ApiResponse(responseCode = "403", description = "Forbidden",
                            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class),
                                    examples = @ExampleObject(value = """
                                    {
                                      "timestamp": "2023-08-31T12:00:00.000+00:00",
                                      "status": 403,
                                      "error": "Forbidden",
                                      "message": "Access Denied"
                                     }""" , summary = "Access Denied Example"))
                            })
            })
    @Parameter(name = "Authorization", description = "JWT Bearer token", in = ParameterIn.HEADER, required = true,
            schema = @Schema(type = "string", format = "Bearer your_jwt_token_here"),
            example = "Bearer your_jwt_token_here")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> me() throws ResourceNotFoundException {

        String userEmail = authenticationService.getAuthenticatedUserEmail();
        if (userEmail == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        UserResponseDTO responseDTO = authenticationService.me(userEmail);
        return new ResponseEntity<>(responseDTO, OK);
    }

}
