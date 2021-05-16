package com.example.dmc.mapper;

import com.example.dmc.entity.Answer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AnswerMapper {

    public Answer toJavaObject(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, Answer.class);
    }
}
