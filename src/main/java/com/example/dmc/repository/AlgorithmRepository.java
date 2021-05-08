package com.example.dmc.repository;

import com.example.dmc.entity.Algorithm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlgorithmRepository extends JpaRepository<Algorithm, Long> {

    Long countAllByNameAndUserId(String name, Long userId);

    List<Algorithm> findAllByUserId(Long userId);


}
