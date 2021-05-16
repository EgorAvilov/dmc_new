package com.example.dmc.service.impl;

import com.example.dmc.entity.Algorithm;
import com.example.dmc.entity.Block;
import com.example.dmc.entity.DataSet;
import com.example.dmc.entity.User;
import com.example.dmc.exception.ServiceException;
import com.example.dmc.mapper.BlockMapper;
import com.example.dmc.repository.AlgorithmRepository;
import com.example.dmc.repository.BlockRepository;
import com.example.dmc.repository.DataSetRepository;
import com.example.dmc.service.BlockService;
import com.example.dmc.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.example.dmc.config.RabbitConfig.ROUTING_KEY_PUT_TASK;
import static com.example.dmc.config.RabbitConfig.TOPIC_EXCHANGE_NAME;

@Service
public class BlockServiceImpl implements BlockService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BlockServiceImpl.class);
    private final AmqpTemplate template;
    private final BlockMapper blockMapper;
    private final AlgorithmRepository algorithmRepository;
    private final DataSetRepository dataSetRepository;
    private final BlockRepository blockRepository;
    private final UserService userService;

    @Autowired
    public BlockServiceImpl(AmqpTemplate template, BlockMapper blockMapper, AlgorithmRepository algorithmRepository, DataSetRepository dataSetRepository, BlockRepository blockRepository, UserService userService) {
        this.template = template;
        this.blockMapper = blockMapper;
        this.algorithmRepository = algorithmRepository;
        this.dataSetRepository = dataSetRepository;
        this.blockRepository = blockRepository;
        this.userService = userService;
    }

    @Override
    public Block create(Block block) throws IOException {
        LOGGER.info("Create block");
        Block finalBlock = block;
        User user = userService.getCurrentUser();
        Algorithm algorithm = algorithmRepository.findByNameAndUserId(block.getAlgorithm()
                                                                .getName(),user.getId())
                                                 .orElseThrow(() -> new ServiceException("No algorithm with such name: " + finalBlock.getAlgorithm()
                                                                                                                                   .getName()));
        DataSet dataSet = dataSetRepository.findByNameAndUserId(block.getDataSet()
                                                          .getName(),user.getId())
                                           .orElseThrow(() -> new ServiceException("No data set with such name: " + finalBlock.getDataSet()
                                                                                                                            .getName()));
        block.setId(UUID.randomUUID()
                        .toString());
        block.setAlgorithm(algorithm);
        block.setDataSet(dataSet);
        block.setUser(user);

        String json = blockMapper.toJSON(block);
        block = blockRepository.save(block);
        LOGGER.info("Send block to node: " + block.getId());
        new Thread(() ->  template.convertAndSend(TOPIC_EXCHANGE_NAME, ROUTING_KEY_PUT_TASK, json)).start();
        return block;
    }

    @Override
    public List<Block> findAll() {
        LOGGER.info("Find all blocks");
        User user = userService.getCurrentUser();
        return blockRepository.findAllByUserId(user.getId());
    }

    @Override
    public Block findById(String id) {
        LOGGER.info("Find block by id: "+ id);
        return blockRepository.findById(id);
    }
}
