package com.example.dmc.service;

import com.example.dmc.entity.DataSet;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DataSetService {

    DataSet create(MultipartFile dataGetter, MultipartFile dataSplitter, MultipartFile dataSaver, String link,String name) throws IOException;

    List<DataSet> findAll();

    boolean dataSetExists(DataSet dataSet);

}
