package com.example.dmc.controller;

import com.example.dmc.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api/node")
public class AnswerController {

    private final AnswerService answerService;

    @Autowired
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping("/create")
    public void create(@RequestBody String json) throws IOException {
        answerService.create(json);
    }

    @GetMapping("/get_stata")
    public void get_stats(@RequestBody String id) {
        System.out.println(id);
    }
}

