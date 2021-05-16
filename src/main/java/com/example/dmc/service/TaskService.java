package com.example.dmc.service;

import com.example.dmc.entity.Task;
import com.example.dmc.entity.Type;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TaskService {

    Task create(MultipartFile file,
                List<Type> inputType,
                List<Type> outputType) throws IOException;

    List<Task> findAll();
}
