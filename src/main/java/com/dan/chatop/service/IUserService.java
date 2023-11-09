package com.dan.chatop.service;

import com.dan.chatop.dto.UserRegistrationDto;
import com.dan.chatop.exception.ResourceNotFoundException;
import com.dan.chatop.model.User;

import java.util.List;

public interface IUserService{

    List<User> getAllUsers();

    User getUserById(Long id);

    User addUser(User user);

    User registerUser(UserRegistrationDto registrationDto);

    User updateUser(Long userId, User updatedUser) throws ResourceNotFoundException;

    void deleteUser(Long id) throws ResourceNotFoundException;

    boolean existsById(Long id);

    User infoUser(Long id);
}
