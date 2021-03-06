package com.example.dmc.service.impl;

import com.example.dmc.entity.Role;
import com.example.dmc.entity.User;
import com.example.dmc.exception.ServiceException;
import com.example.dmc.repository.UserRepository;
import com.example.dmc.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, @Lazy BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public User findByUsername(String username) {
        LOGGER.info("Find user by username: " + username);
        return userRepository.findByUsername(username);
    }

    @Override
    public User create(User user) {
        LOGGER.info("Create user " + user.getUsername());
        if (userExists(user)) {
            LOGGER.error("User with such username already exists: " + user.getUsername());
            throw new ServiceException("User with such username already exists");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        user.setUserRole(Collections.singletonList(Role.USER));
        return userRepository.save(user);
    }

    @Override
    public User getCurrentUser() {
        LOGGER.info("Get current user");
        Authentication authentication = SecurityContextHolder.getContext()
                                                             .getAuthentication();
        String currentPrincipalName = authentication.getName();
        return findByUsername(currentPrincipalName);
    }

    @Override
    public boolean userExists(User user) {
        LOGGER.info("Check for existing user: " + user.getUsername());
        return userRepository.countAllByUsername(user.getUsername()) != 0;
    }
}
