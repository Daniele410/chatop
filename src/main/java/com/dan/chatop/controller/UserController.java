package com.dan.chatop.controller;

import com.dan.chatop.dto.UserResponseDTO;
import com.dan.chatop.exception.UserNotFoundException;
import com.dan.chatop.model.User;
import com.dan.chatop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponseDTO> retrieveUserById(@PathVariable Long id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
        UserResponseDTO meResponse =
                new UserResponseDTO(user.get().getId(), user.get().getName(), user.get().getEmail(), user.get().getCreatedAt(), user.get().getUpdatedAt());
        return ResponseEntity.ok(meResponse);
    }
}
