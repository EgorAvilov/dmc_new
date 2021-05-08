package com.example.dmc.controller;

import com.example.dmc.dto.AuthenticationDto;
import com.example.dmc.entity.User;
import com.example.dmc.security.jwt.JwtTokenProvider;
import com.example.dmc.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
public class AuthenticationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager,
                                    JwtTokenProvider jwtTokenProvider,
                                    UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDto authenticationDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationDto.getUsername(), authenticationDto.getPassword()));
            User user = userService.findByUsername(authenticationDto.getUsername());
            if (user == null) {
                LOGGER.error("User with username: " + authenticationDto.getUsername() + " not found");
                throw new UsernameNotFoundException("User with username: " + authenticationDto.getUsername() + " not found");
            }
            String token = jwtTokenProvider.createToken(authenticationDto.getUsername(), user.getUserRole());
            Map<String, String> response = new HashMap<>();
            response.put("token", "Bearer_" + token);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            LOGGER.error(e.getMessage());
            return new ResponseEntity<>("Invalid username or password", HttpStatus.FORBIDDEN);
        }
    }
}