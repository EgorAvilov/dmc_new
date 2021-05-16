package com.example.dmc.repository;

import com.example.dmc.entity.DataSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DataSetRepository extends JpaRepository<DataSet, Long> {

    List<DataSet> findAllByUserId(Long userId);

    Long countAllByNameAndUserId(String name, Long userId);

    Optional<DataSet> findByNameAndUserId(String name, Long userId);
}
