package com.example.dmc.service.impl;

import com.example.dmc.entity.Algorithm;
import com.example.dmc.entity.Task;
import com.example.dmc.entity.User;
import com.example.dmc.exception.ServiceException;
import com.example.dmc.repository.AlgorithmRepository;
import com.example.dmc.repository.TaskRepository;
import com.example.dmc.service.AlgorithmService;
import com.example.dmc.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AlgorithmServiceImpl implements AlgorithmService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AlgorithmServiceImpl.class);
    private final AlgorithmRepository algorithmRepository;
    private final TaskRepository taskRepository;
    private final UserService userService;

    @Autowired
    public AlgorithmServiceImpl(AlgorithmRepository algorithmRepository, TaskRepository taskRepository, UserService userService) {
        this.algorithmRepository = algorithmRepository;
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    @Override
    public Algorithm create(Algorithm algorithm) {
        LOGGER.info("Create algorithm:" + algorithm.getName());
        User user = userService.getCurrentUser();
        algorithm.setUser(user);
        if (algorithm.getTasks()
                     .size() == 0) {
            LOGGER.error("Tasks list can't be empty");
            throw new ServiceException("Tasks list can't be empty");
        }
        if (algorithmExists(algorithm)) {
            LOGGER.error("Algorithm with such name already exists: " + algorithm.getName());
            throw new ServiceException("Algorithm with such name already exists: " + algorithm.getName());
        }
        List<Task> tasks = algorithm.getTasks()
                                    .stream()
                                    .map(Task::getId)
                                    .filter(Objects::nonNull)
                                    .map((taskId) -> taskRepository.findById(taskId)
                                                                   .orElseThrow(() -> new ServiceException("No Task with such id: " + taskId)))
                                    .collect(Collectors.toList());
        if (tasks.size() == 0) {
            LOGGER.error("Tasks list can't be empty");
            throw new ServiceException("Tasks list can't be empty");
        }

        for (int i = 0; i < tasks.size() - 1; i++) {
            if (!tasks.get(i)
                      .getOutputType()
                      .equals(tasks.get(i + 1)
                                   .getInputType())) {
                throw new ServiceException("Output type of task " + tasks.get(i)
                                                                         .getId() + " should be equal to input type of task " + tasks.get(i + 1)
                                                                                                                                     .getId());
            }
        }


        algorithm.setTasks(tasks);
        return algorithmRepository.save(algorithm);
    }

    @Override
    public boolean algorithmExists(Algorithm algorithm) {
        LOGGER.info("Check for for existing algorithm:" + algorithm.getName());
        return algorithmRepository.countAllByNameAndUserId(algorithm.getName(), algorithm.getUser()
                                                                                         .getId()) != 0;
    }

    @Override
    public List<Algorithm> findAll() {
        LOGGER.info("Find all algorithms");
        User user = userService.getCurrentUser();
        return algorithmRepository.findAllByUserId(user.getId());
    }


}
