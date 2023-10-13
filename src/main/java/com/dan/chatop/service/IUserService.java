package com.dan.chatop.service;

import com.dan.chatop.model.User;

import java.util.List;

public interface IUserService {

    List<User> getAllUsers();

    User getUserById(Long id);

    User addUser(User user);

    User updateUser(User user);

    void deleteUser(Long id);
}
