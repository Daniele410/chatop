package com.dan.chatop.auth;

import com.dan.chatop.dto.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return new ResponseEntity<>(authenticationService.register(request), OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthenticationRequest request) {
        return new ResponseEntity<> (authenticationService.authenticate(request).getToken(), OK);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> me() {
        String userEmail = authenticationService.getAuthenticatedUserEmail();
        if (userEmail == null) {
            throw new IllegalStateException("No authenticated user found");
        }
        UserResponseDTO responseDTO = authenticationService.me(userEmail);
        return new ResponseEntity<> (responseDTO, OK);
    }



}
