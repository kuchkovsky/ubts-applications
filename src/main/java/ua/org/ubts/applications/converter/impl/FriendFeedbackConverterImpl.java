package ua.org.ubts.applications.converter.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.org.ubts.applications.converter.FriendFeedbackConverter;
import ua.org.ubts.applications.dto.FriendFeedbackDto;
import ua.org.ubts.applications.entity.FriendFeedbackEntity;

@Component
public class FriendFeedbackConverterImpl implements FriendFeedbackConverter {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public FriendFeedbackEntity convertToEntity(FriendFeedbackDto dto) {
        return modelMapper.map(dto, FriendFeedbackEntity.class);
    }

    @Override
    public FriendFeedbackDto convertToDto(FriendFeedbackEntity entity) {
        return modelMapper.map(entity, FriendFeedbackDto.class);
    }

}
