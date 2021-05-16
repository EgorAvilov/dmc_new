package com.example.dmc.service.impl;

import com.example.dmc.entity.Answer;
import com.example.dmc.entity.User;
import com.example.dmc.exception.ServiceException;
import com.example.dmc.mapper.AnswerMapper;
import com.example.dmc.repository.AnswerRepository;
import com.example.dmc.repository.BlockRepository;
import com.example.dmc.service.AnswerService;
import com.example.dmc.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.example.dmc.config.RabbitConfig.ROUTING_KEY_GET_STATISTICS;
import static com.example.dmc.config.RabbitConfig.TOPIC_EXCHANGE_NAME;

@Service
public class AnswerServiceImpl implements AnswerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnswerServiceImpl.class);
    private final AnswerMapper answerMapper;
    private final AmqpTemplate template;
    private final AnswerRepository answerRepository;
    private final BlockRepository blockRepository;
    private final UserService userService;

    @Autowired
    public AnswerServiceImpl(AnswerMapper answerMapper, AmqpTemplate template, AnswerRepository answerRepository, BlockRepository blockRepository, UserService userService) {
        this.answerMapper = answerMapper;
        this.template = template;
        this.answerRepository = answerRepository;
        this.blockRepository = blockRepository;
        this.userService = userService;
    }

    @Override
    public Answer create(String json) throws IOException {
        LOGGER.info("Create answer:" + json);
        Answer answer = answerMapper.toJavaObject(json);
        User user = blockRepository.findById(answer.getId())
                                   .getUser();
        answer.setUser(user);
        return answerRepository.save(answer);
    }

    @Override
    public Answer findById(String id) throws IOException {
        LOGGER.info("Find answer by id:" + id);
        if (!blockRepository.existsById(id)) {
            throw new ServiceException("No Block with such id: " + id);
        }
        if (!answerRepository.existsById(id)) {
            return answerMapper.toJavaObject((String) template.convertSendAndReceive(TOPIC_EXCHANGE_NAME, ROUTING_KEY_GET_STATISTICS, id));
        } else {
            return answerRepository.findById(id);
        }
    }

    @Override
    public List<Answer> findAll() {
        LOGGER.info("Find all answers");
        User user = userService.getCurrentUser();
        return answerRepository.findAllByUserId(user.getId());
    }
}
