package ua.org.ubts.applications.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.org.ubts.applications.dto.*;
import ua.org.ubts.applications.entity.ChurchMinistryEntity;
import ua.org.ubts.applications.entity.MaritalDataEntity;
import ua.org.ubts.applications.entity.ResidenceEntity;
import ua.org.ubts.applications.entity.StudentEntity;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mapEntitiesToDto(modelMapper);
        mapDtoToEntities(modelMapper);
        return modelMapper;
    }

    private void mapEntitiesToDto(ModelMapper modelMapper) {
        modelMapper.createTypeMap(ResidenceEntity.class, ResidenceDto.class)
                .addMapping(residenceEntity -> residenceEntity.getCountry().getName(), ResidenceDto::setCountry);
        modelMapper.createTypeMap(MaritalDataEntity.class, MaritalDataDto.class)
                .addMapping(maritalDataEntity -> maritalDataEntity.getStatus().getName(), MaritalDataDto::setStatus);
        modelMapper.createTypeMap(ChurchMinistryEntity.class, ChurchMinistryDto.class)
                .addMapping(churchMinistryEntity -> churchMinistryEntity.getType().getName(), ChurchMinistryDto::setType);
        modelMapper.createTypeMap(StudentEntity.class, StudentDto.class)
                .addMapping(studentEntity -> studentEntity.getEducation().getName(), StudentDto::setEducation)
                .addMapping(studentEntity -> studentEntity.getHowFindOut().getName(), StudentDto::setHowFindOut);
        modelMapper.createTypeMap(StudentEntity.class, StudentListItemDto.class)
                .addMapping(StudentEntity::getFullSlavicName, StudentListItemDto::setName);
    }

    private void mapDtoToEntities(ModelMapper modelMapper) {
        modelMapper.createTypeMap(ResidenceDto.class, ResidenceEntity.class)
                .<String>addMapping(ResidenceDto::getCountry,
                        (residenceEntity, s) -> residenceEntity.getCountry().setName(s));
        modelMapper.createTypeMap(MaritalDataDto.class, MaritalDataEntity.class)
                .<String>addMapping(MaritalDataDto::getStatus,
                        (maritalDataEntity, s) -> maritalDataEntity.getStatus().setName(s));
        modelMapper.createTypeMap(ChurchMinistryDto.class, ChurchMinistryEntity.class)
                .<String>addMapping(ChurchMinistryDto::getType,
                        (churchMinistryEntity, s) -> churchMinistryEntity.getType().setName(s));
        modelMapper.createTypeMap(StudentDto.class, StudentEntity.class)
                .<String>addMapping(StudentDto::getEducation,
                        (studentEntity, s) -> studentEntity.getEducation().setName(s))
                .<String>addMapping(StudentDto::getHowFindOut,
                        (studentEntity, s) -> studentEntity.getHowFindOut().setName(s));
    }

}
