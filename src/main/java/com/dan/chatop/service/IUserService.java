package com.dan.chatop.service;

import com.dan.chatop.dto.UserRegistrationDto;
import com.dan.chatop.model.User;

import java.util.List;

public interface IUserService /*extends UserDetailsService */{

    List<User> getAllUsers();

    /*UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;*/

    User getUserById(Long id);

    User addUser(User user);


    User registerUser(UserRegistrationDto registrationDto);

    User updateUser(Long userId, User updatedUser);

    void deleteUser(Long id);

    boolean existsById(Long id);

    User infoUser(Long id);
}
