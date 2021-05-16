package com.example.dmc.repository;

import com.example.dmc.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Answer findById(String id);

    List<Answer> findAllByUserId(Long id);

    Boolean existsById(String id);
}
