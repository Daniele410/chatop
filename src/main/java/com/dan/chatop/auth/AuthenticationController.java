package com.dan.chatop.auth;

import com.dan.chatop.dto.UserResponseDTO;
import com.dan.chatop.exception.ResourceNotFoundException;
import com.dan.chatop.model.User;
import com.dan.chatop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

@CrossOrigin(origins = "*")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterRequest request) throws Exception {
        Optional<User> userEmail = userRepository.findByEmail(request.getEmail());
        if (userEmail.isPresent()) {

            return new ResponseEntity<>( "Email already exists", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(authenticationService.register(request), OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        return new ResponseEntity<>(authenticationService.authenticate(request), OK);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> me() throws ResourceNotFoundException {
        String userEmail = authenticationService.getAuthenticatedUserEmail();
        if (userEmail == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED) ;
        }
        UserResponseDTO responseDTO = authenticationService.me(userEmail);
        return new ResponseEntity<>(responseDTO, OK);
    }


}
