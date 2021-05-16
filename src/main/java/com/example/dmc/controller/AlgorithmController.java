package com.example.dmc.controller;

import com.example.dmc.entity.Algorithm;
import com.example.dmc.service.AlgorithmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/algorithms")
@PreAuthorize("hasAuthority('USER')")
public class AlgorithmController {

    private final AlgorithmService algorithmService;

    @Autowired
    public AlgorithmController(AlgorithmService algorithmService) {
        this.algorithmService = algorithmService;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid Algorithm algorithm) {
        return new ResponseEntity<>(algorithmService.create(algorithm), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity findAll() {
        return new ResponseEntity<>(algorithmService.findAll(), HttpStatus.OK);
    }
}



