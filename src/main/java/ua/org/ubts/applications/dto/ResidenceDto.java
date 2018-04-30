package ua.org.ubts.applications.dto;

import lombok.Data;

@Data
public class ResidenceDto extends BaseDto {

    private String country;

    private String region;

    private String cityVillage;

    private String index;

    private String district;

    private String street;

    private String house;

    private String apartment;

}
