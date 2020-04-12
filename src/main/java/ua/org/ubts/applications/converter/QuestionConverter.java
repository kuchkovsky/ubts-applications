package ua.org.ubts.applications.converter;

import ua.org.ubts.applications.dto.QuestionDto;
import ua.org.ubts.applications.entity.QuestionEntity;

import java.util.List;

public interface QuestionConverter extends GenericConverter<QuestionDto, QuestionEntity> {

    QuestionDto convertToListDto(QuestionEntity entity);

    List<QuestionDto> convertToListDto(List<QuestionEntity> entities);
}
