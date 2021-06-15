package com.example.dmc.controller;

import com.example.dmc.entity.Type;
import com.example.dmc.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/tasks")
@PreAuthorize("hasAuthority('USER')")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity create(@RequestPart(value = "file", required = false) MultipartFile file,
                                 @RequestParam(value = "inputType", required = false) List<Type> inputType,
                                 @RequestParam(value = "outputType", required = false) List<Type> outputType) throws IOException {
        return new ResponseEntity<>(taskService.create(file, inputType, outputType), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity findAll() {
        return new ResponseEntity<>(taskService.findAll(), HttpStatus.OK);
    }
}
