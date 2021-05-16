package com.example.dmc.service;

import com.example.dmc.entity.Block;

import java.io.IOException;
import java.util.List;

public interface BlockService {

    Block create(Block block) throws IOException;

    List<Block> findAll();

    Block findById(String id);

}
