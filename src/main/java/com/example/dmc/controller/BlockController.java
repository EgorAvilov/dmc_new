package com.example.dmc.controller;

import com.example.dmc.entity.Block;
import com.example.dmc.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api/blocks")
@PreAuthorize("hasAuthority('USER')")
public class BlockController {

    private final BlockService blockService;

    @Autowired
    public BlockController(BlockService blockService) {
        this.blockService = blockService;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody Block block) throws IOException {
        return new ResponseEntity<>(blockService.create(block), HttpStatus.CREATED);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity findById(@PathVariable String id) {
        return new ResponseEntity<>(blockService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity findAll() {
        return new ResponseEntity<>(blockService.findAll(), HttpStatus.OK);
    }
}
