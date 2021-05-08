package com.example.dmc.service;

import com.example.dmc.entity.User;

public interface UserService {
    User findByUsername(String username);

    User create(User user);

    User getCurrentUser();

    boolean userExists(User user);
}
