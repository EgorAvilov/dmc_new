package com.example.dmc.controller;

import com.example.dmc.service.DataSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api/datasets")
@PreAuthorize("hasAuthority('USER')")
public class DataSetController {
    private final DataSetService dataSetService;

    @Autowired
    public DataSetController(DataSetService dataSetService) {
        this.dataSetService = dataSetService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity create(@RequestPart("dataGetter") MultipartFile dataGetter,
                                 @RequestPart("dataSplitter") MultipartFile dataSplitter,
                                 @RequestPart("dataSaver") MultipartFile dataSaver,
                                 @RequestParam("link") String link) throws IOException {
        return new ResponseEntity<>(dataSetService.create(dataGetter, dataSplitter, dataSaver, link), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity findAll() {
        return new ResponseEntity<>(dataSetService.findAll(), HttpStatus.OK);
    }
}
