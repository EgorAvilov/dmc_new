package com.example.dmc.repository;

import com.example.dmc.entity.DataSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataSetRepository extends JpaRepository<DataSet, Long> {
    // Long countAllByFileNameAndUserId(String name, Long userId);

    List<DataSet> findAllByUserId(Long userId);
}
