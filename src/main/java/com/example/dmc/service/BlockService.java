package com.example.dmc.service;

import com.example.dmc.entity.Block;

import java.io.IOException;

public interface BlockService {

    Block create(Block block) throws IOException;
}
