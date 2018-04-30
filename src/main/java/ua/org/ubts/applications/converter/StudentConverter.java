package ua.org.ubts.applications.converter;

import ua.org.ubts.applications.dto.StudentDto;
import ua.org.ubts.applications.dto.StudentListItemDto;
import ua.org.ubts.applications.entity.StudentEntity;

import java.util.List;

public interface StudentConverter extends GenericConverter<StudentDto, StudentEntity> {

    StudentListItemDto convertToListDto(StudentEntity entity);

    List<StudentListItemDto> convertToListDto(List<StudentEntity> entities);

}
