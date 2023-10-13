package com.dan.chatop.service;

import com.dan.chatop.model.User;
import com.dan.chatop.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService{

    private static final Logger log = LogManager.getLogger("UserServiceImpl");

    @Autowired
    UserRepository userRepository;

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
    public User updateUser(User user) {
        log.info("update user");
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        log.info("delete user");
        userRepository.deleteById(id);
    }
}
