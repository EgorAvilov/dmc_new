package com.example.dmc.dto;

import com.example.dmc.entity.Type;
import lombok.Data;

@Data
public class TaskDto {

    private Type inputType;

    private Type outputType;

    private String fileName;
}
