package ua.org.ubts.applications.converter.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.org.ubts.applications.converter.PastorFeedbackConverter;
import ua.org.ubts.applications.dto.PastorFeedbackDto;
import ua.org.ubts.applications.entity.PastorFeedbackEntity;

@Component
public class PastorFeedbackConverterImpl implements PastorFeedbackConverter {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PastorFeedbackEntity convertToEntity(PastorFeedbackDto dto) {
        return modelMapper.map(dto, PastorFeedbackEntity.class);
    }

    @Override
    public PastorFeedbackDto convertToDto(PastorFeedbackEntity entity) {
        return modelMapper.map(entity, PastorFeedbackDto.class);
    }
}
