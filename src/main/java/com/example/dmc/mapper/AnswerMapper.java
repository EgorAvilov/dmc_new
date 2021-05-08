package com.example.dmc.mapper;

import com.example.dmc.dto.AnswerDto;
import com.example.dmc.entity.Answer;
import com.example.dmc.entity.Block;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AnswerMapper {

    public Answer dtoToEntity(AnswerDto answerDto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(answerDto, Answer.class);
    }

    public AnswerDto toJavaObject(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, AnswerDto.class);
    }
}
