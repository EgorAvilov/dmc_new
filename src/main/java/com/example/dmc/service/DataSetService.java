package com.example.dmc.service;

import com.example.dmc.dto.DataSetDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DataSetService {

    DataSetDto create(MultipartFile dataGetter, MultipartFile dataSplitter, MultipartFile dataSaver, String link) throws IOException;

    List<DataSetDto> findAll();

}
