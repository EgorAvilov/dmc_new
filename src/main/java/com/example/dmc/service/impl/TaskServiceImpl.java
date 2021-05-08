package com.example.dmc.service.impl;

import com.example.dmc.dto.TaskDto;
import com.example.dmc.entity.Task;
import com.example.dmc.entity.Type;
import com.example.dmc.entity.User;
import com.example.dmc.exception.ServiceException;
import com.example.dmc.mapper.TaskMapper;
import com.example.dmc.repository.TaskRepository;
import com.example.dmc.service.TaskService;
import com.example.dmc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, UserService userService, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.taskMapper = taskMapper;
    }

    @Override
    public Task create(MultipartFile file, Type inputType, Type outputType) throws IOException {
        String PYTHON_CONTENT_TYPE = "text/x-python";
        if (!Objects.equals(file.getContentType(), PYTHON_CONTENT_TYPE)) {
            throw new ServiceException("Wrong content type of file. Should be .py");
        }
        User user = userService.getCurrentUser();
        Task task = new Task();
        task.setUser(user);
        task.setFileName(file
                .getOriginalFilename());
        task.setFile(file
                .getBytes());
        task.setInputType(Type.valueOf(inputType
                .toString()));
        task.setOutputType(Type.valueOf(outputType
                .toString()));
        return taskRepository.save(task);
    }

    @Override
    public List<TaskDto> findAll() {
        User user = userService.getCurrentUser();
        List<Task> tasks = taskRepository.findAllByUserId(user.getId());
        return taskMapper.entityToDto(tasks);
    }
}
