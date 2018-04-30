package ua.org.ubts.applications.converter.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.org.ubts.applications.converter.YearConverter;
import ua.org.ubts.applications.dto.YearDto;
import ua.org.ubts.applications.entity.YearEntity;

@Component
public class YearConverterImpl implements YearConverter {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public YearEntity convertToEntity(YearDto dto) {
        return modelMapper.map(dto, YearEntity.class);
    }

    @Override
    public YearDto convertToDto(YearEntity entity) {
        return modelMapper.map(entity, YearDto.class);
    }

}
