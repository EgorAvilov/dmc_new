package com.example.dmc.repository;

import com.example.dmc.entity.Algorithm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlgorithmRepository extends JpaRepository<Algorithm, Long> {

    Long countAllByNameAndUserId(String name, Long userId);

    List<Algorithm> findAllByUserId(Long userId);

    Optional<Algorithm> findByNameAndUserId(String name, Long userId);

    Algorithm findByNameAndUserId(String name);
}
