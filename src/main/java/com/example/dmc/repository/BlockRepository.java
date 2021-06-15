package com.example.dmc.repository;

import com.example.dmc.entity.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockRepository extends JpaRepository<Block, Long> {

    List<Block> findAllByUserId(Long userId);

    Block findById(String id);

    Boolean existsById(String id);
}
