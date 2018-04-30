package ua.org.ubts.applications.dto;

import lombok.Data;

@Data
public class PersonDto extends BaseDto {

    private String firstName;

    private String middleName;

    private String lastName;

    private String birthDate;

    private String phone1;

    private String phone2;

    private String email;

    private String job;

    private ResidenceDto residence;

    private String education;

}
