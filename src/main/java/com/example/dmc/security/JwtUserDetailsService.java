package com.example.dmc.security;

import com.example.dmc.entity.User;
import com.example.dmc.security.jwt.JwtUser;
import com.example.dmc.security.jwt.JwtUserFactory;
import com.example.dmc.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUserDetailsService.class);
    private final UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if (user == null) {
            LOGGER.error("User with username: " + username + " not found");
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        }
        JwtUser jwtUser = JwtUserFactory.create(user);
        LOGGER.info("IN loadUserByUsername - user with username: {} successfully loaded", username);
        return jwtUser;
    }
}