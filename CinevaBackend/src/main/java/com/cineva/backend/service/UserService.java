package com.cineva.backend.service;

import com.cineva.backend.entity.User;
import com.cineva.backend.repository.UserRepository;

import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        logger.info("UserService initialized");
    }

    @Transactional
    public User signIn(String emailOrPhone, String password) throws Exception {
        logger.debug("Attempting sign-in for emailOrPhone: {}", emailOrPhone);

        Optional<User> userOptional = userRepository.findByEmailOrPhone(emailOrPhone);
        if (userOptional.isEmpty()) {
            throw new Exception("User not found with email or phone: " + emailOrPhone);
        }

        User user = userOptional.get();

        if (!user.getPassword().equals(password)) {
            throw new Exception("Invalid password");
        }

        // Example: update last login timestamp
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);  // <-- This persists changes

        logger.info("Sign-in successful for user: {}", emailOrPhone);
        return user;
    }
}