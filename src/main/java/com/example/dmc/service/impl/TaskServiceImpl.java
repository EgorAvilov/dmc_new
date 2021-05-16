package com.example.dmc.service.impl;

import com.example.dmc.entity.Task;
import com.example.dmc.entity.Type;
import com.example.dmc.entity.User;
import com.example.dmc.exception.ServiceException;
import com.example.dmc.repository.TaskRepository;
import com.example.dmc.service.TaskService;
import com.example.dmc.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class TaskServiceImpl implements TaskService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskServiceImpl.class);
    private final TaskRepository taskRepository;
    private final UserService userService;


    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;

    }

    @Override
    public Task create(MultipartFile file, List<Type> inputType, List<Type> outputType) throws IOException {
        LOGGER.info("Create task: " + file.getOriginalFilename());
        fileCheck(file);
        inputTypeCheck(inputType);
        outputTypeCheck(outputType);

        User user = userService.getCurrentUser();
        Task task = new Task();
        task.setUser(user);
        task.setFileName(file
                .getOriginalFilename());
        task.setFile(file
                .getBytes());
        task.setInputType(Type.valueOf(inputType.get(0)
                                                .toString()));
        task.setOutputType(Type.valueOf(outputType.get(0)
                                                  .toString()));
        return taskRepository.save(task);
    }

    @Override
    public List<Task> findAll() {
        LOGGER.info("Find all tasks");
        User user = userService.getCurrentUser();
        return taskRepository.findAllByUserId(user.getId());
    }

    private void fileCheck(MultipartFile file) {
        if (file == null) {
            throw new ServiceException("File can't be null");
        }

        String PYTHON_CONTENT_TYPE = "text/x-python";
        if (!Objects.equals(file.getContentType(), PYTHON_CONTENT_TYPE)) {
            LOGGER.error("Wrong content type of file. Should be .py: " + file.getOriginalFilename());
            throw new ServiceException("Wrong content type of file. Should be .py");
        }
    }

    private void inputTypeCheck(List<Type> inputType) {
        if (inputType == null) {
            throw new ServiceException("Input type can't be null");
        }
        if (inputType.size() != 1) {
            throw new ServiceException("Should be 1 input type");
        }
    }

    private void outputTypeCheck(List<Type> outputType) {
        if (outputType == null) {
            throw new ServiceException("Input type can't be null");
        }
        if (outputType.size() != 1) {
            throw new ServiceException("Should be 1 output type");
        }
    }
}
