package ua.org.ubts.applications.converter.impl;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.org.ubts.applications.converter.StudentConverter;
import ua.org.ubts.applications.dto.StudentDto;
import ua.org.ubts.applications.dto.StudentListItemDto;
import ua.org.ubts.applications.entity.*;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StudentConverterImpl implements StudentConverter {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public StudentEntity convertToEntity(StudentDto dto) {
        return modelMapper.map(dto, StudentEntity.class);
    }

    @Override
    public StudentDto convertToDto(StudentEntity entity) {
        if (entity.getHowFindOut() == null) {
            HowFindOutEntity howFindOutEntity = new HowFindOutEntity();
            howFindOutEntity.setName("Від Бога");
            entity.setHowFindOut(howFindOutEntity);
        }
        return modelMapper.map(entity, StudentDto.class);
    }

    @Override
    public StudentListItemDto convertToListDto(StudentEntity entity) {
        return modelMapper.map(entity, StudentListItemDto.class);
    }

    @Override
    public List<StudentListItemDto> convertToListDto(List<StudentEntity> entities) {
        return entities.stream().map(this::convertToListDto).collect(Collectors.toList());
    }

}
