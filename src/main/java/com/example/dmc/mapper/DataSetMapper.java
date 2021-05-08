package com.example.dmc.mapper;

import com.example.dmc.dto.DataSetDto;
import com.example.dmc.entity.DataSet;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DataSetMapper {
    public DataSetDto entityToDto(DataSet dataSet) {
        DataSetDto dataSetDto = new DataSetDto();
        dataSetDto.setDataGetter(dataSet.getDataGetter()
                                        .getFileName());
        dataSetDto.setDataSaver(dataSet.getDataSaver()
                                       .getFileName());
        dataSetDto.setDataSplitter(dataSet.getDataSplitter()
                                          .getFileName());
        dataSetDto.setLink(dataSet.getLink());
        return dataSetDto;
    }
    public List<DataSetDto> entityToDto(List<DataSet> dataSetList) {
        return dataSetList.stream()
                     .map(this::entityToDto)
                     .collect(Collectors.toList());
    }
}
