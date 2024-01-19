package com.example.readymadeShop.service;

// src/main/java/com/example/readymadeShop/service/UserService.java

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.readymadeShop.model.User;
import com.example.readymadeShop.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }
}

