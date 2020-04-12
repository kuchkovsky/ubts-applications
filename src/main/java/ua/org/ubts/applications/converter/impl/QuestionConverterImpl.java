package ua.org.ubts.applications.converter.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import ua.org.ubts.applications.converter.QuestionConverter;
import ua.org.ubts.applications.converter.StudentConverter;
import ua.org.ubts.applications.dto.QuestionDto;
import ua.org.ubts.applications.dto.StudentDto;
import ua.org.ubts.applications.dto.StudentListItemDto;
import ua.org.ubts.applications.entity.HowFindOutEntity;
import ua.org.ubts.applications.entity.QuestionEntity;
import ua.org.ubts.applications.entity.StudentEntity;
import ua.org.ubts.applications.service.StudentFilesService;

import java.util.List;
import java.util.stream.Collectors;

public class QuestionConverterImpl implements QuestionConverter {
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public QuestionEntity convertToEntity(QuestionDto dto) {
        return modelMapper.map(dto, QuestionEntity.class);
    }

    @Override
    public QuestionDto convertToDto(QuestionEntity entity) {
        QuestionDto questionDto = modelMapper.map(entity, QuestionDto.class);
        return questionDto;
    }

    @Override
    public QuestionDto convertToListDto(QuestionEntity entity) {
        return modelMapper.map(entity, QuestionDto.class);
    }

    @Override
    public List<QuestionDto> convertToListDto(List<QuestionEntity> entities) {
        return entities.stream().map(this::convertToListDto).collect(Collectors.toList());
    }
}
