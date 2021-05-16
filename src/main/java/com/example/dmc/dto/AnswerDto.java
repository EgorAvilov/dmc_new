package com.example.dmc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AnswerDto {
    @JsonProperty("taskId")
    private String id;

    @JsonProperty("status")
    private String status;

    @JsonProperty("timeSpent")
    private String timeSpent;

    @JsonProperty("message")
    private String message;

}
