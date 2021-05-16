package com.example.dmc.service;

import com.example.dmc.entity.Algorithm;

import java.util.List;

public interface AlgorithmService {

    Algorithm create(Algorithm algorithm);

    boolean algorithmExists(Algorithm algorithm);

    List<Algorithm> findAll();


}
