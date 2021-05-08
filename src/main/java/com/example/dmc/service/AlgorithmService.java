package com.example.dmc.service;

import com.example.dmc.entity.Algorithm;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AlgorithmService {

    Algorithm create(Algorithm algorithm);

    boolean algorithmExists(Algorithm algorithm);

    List<Algorithm> findAll();
}
