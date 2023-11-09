package com.dan.chatop.service;

import com.dan.chatop.dto.UserRegistrationDto;
import com.dan.chatop.exception.ResourceNotFoundException;
import com.dan.chatop.model.User;
import com.dan.chatop.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class UserServiceImpl implements IUserService {

    private static final Logger log = LogManager.getLogger("UserServiceImpl");

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        log.info("get all users");
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        log.info("get user by id");
        return userRepository.findById(id).get();
    }

    @Override
    public User addUser(User user) {
        log.info("add user");
        return userRepository.save(user);
    }

    @Override
    public User registerUser(UserRegistrationDto registrationDto) {
        User user = new User(registrationDto.getEmail(), registrationDto.getName(), registrationDto.getPassword());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long userId, User updatedUser) throws ResourceNotFoundException {
        log.info("update user");
        if (userRepository.existsById(userId)) {
            updatedUser.setId(userId);
            User user = userRepository.findById(userId).get();
            updatedUser.setCreatedAt(LocalDateTime.now());
            updatedUser.setUpdatedAt(LocalDateTime.now());
            return userRepository.save(updatedUser);
        } else {
            throw new ResourceNotFoundException("User with ID " + userId + " not found");
        }
    }

    @Override
    public void deleteUser(Long userId) throws ResourceNotFoundException {
        log.info("delete user");
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        } else {
            log.info("id:" + userId + "not present in Database");
            throw new ResourceNotFoundException("User with ID " + userId + " not found");
        }

    }

    @Override
    public boolean existsById(Long id) {
        log.info("check if user exists");
        if (userRepository.existsById(id)) {
            return true;
        }
        return false;
    }

    @Override
    public User infoUser(Long id) {
        log.info("get user by id");
        User user = userRepository.findById(id).get();
        UserRegistrationDto userDto = new UserRegistrationDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setCreatedAt(user.getCreatedAt());
        userDto.setUpdatedAt(user.getUpdatedAt());
        return userRepository.findById(id).orElseThrow();
    }
}
