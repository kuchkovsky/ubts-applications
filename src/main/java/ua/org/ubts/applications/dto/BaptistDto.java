package ua.org.ubts.applications.dto;

import lombok.Data;

@Data
public class BaptistDto extends PersonDto {

    private MaritalDataDto maritalData;

    private ChurchDataDto churchData;

    private ChurchMinistryDto churchMinistry;

}
