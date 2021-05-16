package com.example.dmc.controller;

import com.example.dmc.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api/answers")
public class AnswerController {

    private final AnswerService answerService;

    @Autowired
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping
    public void create(@RequestBody String json) throws IOException {
        answerService.create(json);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity findById(@PathVariable String id) throws IOException {
        return new ResponseEntity<>(answerService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity findAll() {
        return new ResponseEntity<>(answerService.findAll(), HttpStatus.OK);
    }
}

