package ua.org.ubts.applications.converter.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.org.ubts.applications.converter.ProgramConverter;
import ua.org.ubts.applications.dto.ProgramDto;
import ua.org.ubts.applications.entity.ProgramEntity;

@Component
public class ProgramConverterImpl implements ProgramConverter {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProgramEntity convertToEntity(ProgramDto dto) {
        return modelMapper.map(dto, ProgramEntity.class);
    }

    @Override
    public ProgramDto convertToDto(ProgramEntity entity) {
        return modelMapper.map(entity, ProgramDto.class);
    }

}
