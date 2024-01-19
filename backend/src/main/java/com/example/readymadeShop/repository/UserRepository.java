package com.example.readymadeShop.repository;

// src/main/java/com/example/readymadeShop/repository/UserRepository.java

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.readymadeShop.model.User;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    // You can add custom query methods if needed
    User findByEmailAndPassword(String email, String password);
    Optional<User> findByVerificationToken(String verificationToken);
}
