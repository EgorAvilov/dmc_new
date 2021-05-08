package com.example.dmc.mapper;

import com.example.dmc.dto.TaskDto;
import com.example.dmc.entity.Task;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskMapper {
    public TaskDto entityToDto(Task task) {
        TaskDto taskDto = new TaskDto();
        taskDto.setInputType(task.getInputType());
        taskDto.setOutputType(task.getOutputType());
        taskDto.setFileName(task.getFileName());

        return taskDto;
    }

    public List<TaskDto> entityToDto(List<Task> tasks) {
        return tasks.stream()
                    .map(this::entityToDto)
                    .collect(Collectors.toList());
    }
}
