package com.example.dmc.service.impl;

import com.example.dmc.entity.DataSet;
import com.example.dmc.entity.Task;
import com.example.dmc.entity.User;
import com.example.dmc.exception.ServiceException;
import com.example.dmc.repository.DataSetRepository;
import com.example.dmc.service.DataSetService;
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
public class DataSetServiceImpl implements DataSetService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSetServiceImpl.class);
    private final DataSetRepository dataSetRepository;
    private final UserService userService;

    private final String PYTHON_CONTENT_TYPE = "text/x-python";

    @Autowired
    public DataSetServiceImpl(DataSetRepository dataSetRepository, UserService userService) {
        this.dataSetRepository = dataSetRepository;
        this.userService = userService;
    }

    @Override
    public DataSet create(MultipartFile dataGetter, MultipartFile dataSplitter, MultipartFile dataSaver, String link, String name) throws IOException {
        LOGGER.info("Create dataset");
        dataGetterCheck(dataGetter);
        dataSplitterCheck(dataSplitter);
        dataSaverCheck(dataSaver);
        linkCheck(link);
        nameCheck(name);

        User user = userService.getCurrentUser();
        DataSet dataSet = new DataSet();
        dataSet.setName(name);
        dataSet.setUser(user);
        if (dataSetExists(dataSet)) {
            throw new ServiceException("Data set with such name already exists: " + dataSet.getName());
        }
        Task dataGetterTask = new Task();
        dataGetterTask.setFileName(dataGetter
                .getOriginalFilename());
        dataGetterTask.setFile(dataGetter
                .getBytes());

        Task dataSplitterTask = new Task();
        dataSplitterTask.setFileName(dataSplitter
                .getOriginalFilename());
        dataSplitterTask.setFile(dataSplitter
                .getBytes());

        Task dataSaverTask = new Task();
        dataSaverTask.setFileName(dataSaver
                .getOriginalFilename());
        dataSaverTask.setFile(dataSaver
                .getBytes());

        dataSet.setLink(link);
        dataSet.setDataGetter(dataGetterTask);
        dataSet.setDataSplitter(dataSplitterTask);
        dataSet.setDataSaver(dataSaverTask);

        return dataSetRepository.save(dataSet);
    }

    @Override
    public boolean dataSetExists(DataSet dataSet) {
        LOGGER.info("Check for for existing dataSet:" + dataSet.getName());
        return dataSetRepository.countAllByNameAndUserId(dataSet.getName(), dataSet.getUser()
                                                                                   .getId()) != 0;
    }

    @Override
    public List<DataSet> findAll() {
        LOGGER.info("Find all datasets");
        User user = userService.getCurrentUser();
        return dataSetRepository.findAllByUserId(user.getId());

    }

    private void dataGetterCheck(MultipartFile dataGetter) {
        if (dataGetter == null) {
            throw new ServiceException("dataGetter can't be null");
        }
        if (!Objects.equals(dataGetter.getContentType(), PYTHON_CONTENT_TYPE)) {
            LOGGER.error("Wrong content type of dataGetter. Should be .py: " + dataGetter.getName());
            throw new ServiceException("Wrong content type of dataGetter. Should be .py");
        }
    }

    private void dataSplitterCheck(MultipartFile dataSplitter) {
        if (dataSplitter == null) {
            throw new ServiceException("dataSplitter can't be null");
        }
        if (!Objects.equals(dataSplitter.getContentType(), PYTHON_CONTENT_TYPE)) {
            LOGGER.error("Wrong content type of dataSplitter. Should be .py: " + dataSplitter.getName());
            throw new ServiceException("Wrong content type of dataSplitter. Should be .py");
        }
    }

    private void dataSaverCheck(MultipartFile dataSaver) {
        if (dataSaver == null) {
            throw new ServiceException("dataSaver can't be null");
        }
        if (!Objects.equals(dataSaver.getContentType(), PYTHON_CONTENT_TYPE)) {
            LOGGER.error("Wrong content type of dataSaver. Should be .py: " + dataSaver.getName());
            throw new ServiceException("Wrong content type of dataSaver. Should be .py");
        }
    }

    private void linkCheck(String link) {
        if (link == null) {
            throw new ServiceException("Link can't be null");
        }
    }

    private void nameCheck(String name) {
        if (name == null) {
            throw new ServiceException("name can't be null");
        }
    }
}
