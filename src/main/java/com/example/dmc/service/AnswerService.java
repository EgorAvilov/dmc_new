package com.example.dmc.service;

import com.example.dmc.dto.AnswerDto;
import com.example.dmc.entity.Answer;

import java.io.IOException;
import java.util.List;

public interface AnswerService {
    Answer create(String json) throws IOException;

    Answer findById(String id);

    List<AnswerDto> findAll();
}
