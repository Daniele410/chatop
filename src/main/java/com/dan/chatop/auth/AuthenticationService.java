package com.dan.chatop.auth;

import com.dan.chatop.configuration.JwtService;
import com.dan.chatop.dto.UserResponseDTO;
import com.dan.chatop.exception.ResourceNotFoundException;
import com.dan.chatop.model.Role;
import com.dan.chatop.model.User;
import com.dan.chatop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) throws Exception {
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .createdAt(Date.from(java.time.Instant.now()))
                .updatedAt(Date.from(java.time.Instant.now()))
                .role(Role.USER)
                .build();
        if (userRepository.existsByEmail(user.getEmail())) {
            log.warn("email: " + user.getEmail() + " is present in Database");
            throw new ResourceNotFoundException("User with email " + user.getEmail() + " is present in Database");
        } else {
            userRepository.save(user);

            var jwtToken = jwtService.generateToken(user.getEmail());
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user.getEmail());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public UserResponseDTO me(String userEmail) throws ResourceNotFoundException {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + userEmail + " not found"));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        UserResponseDTO response = new UserResponseDTO();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setCreated_at(user.getCreatedAt());
        response.setUpdated_at(user.getUpdatedAt());

        return response;
    }

    public String getAuthenticatedUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return authentication.getName();
        }
    }

}

