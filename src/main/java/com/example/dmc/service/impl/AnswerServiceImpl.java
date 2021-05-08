package com.example.dmc.service.impl;

import com.example.dmc.dto.AnswerDto;
import com.example.dmc.entity.Answer;
import com.example.dmc.mapper.AnswerMapper;
import com.example.dmc.repository.AnswerRepository;
import com.example.dmc.service.AnswerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnswerServiceImpl.class);
    private final AnswerMapper answerMapper;
    private final AnswerRepository answerRepository;

    @Autowired
    public AnswerServiceImpl(AnswerMapper answerMapper, AnswerRepository answerRepository) {
        this.answerMapper = answerMapper;
        this.answerRepository = answerRepository;

    }

    @Override
    public Answer create(String json) throws IOException {
        AnswerDto answerDto = answerMapper.toJavaObject(json);
        Answer answer = answerMapper.dtoToEntity(answerDto);
        return answerRepository.save(answer);
    }

    @Override
    public Answer findById(String id) {
        return answerRepository.findById(id);
    }

    @Override
    public List<AnswerDto> findAll() {
        return null;
    }
}
