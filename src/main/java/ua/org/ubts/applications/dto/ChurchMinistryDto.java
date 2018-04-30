package ua.org.ubts.applications.dto;

import lombok.Data;

@Data
public class ChurchMinistryDto extends BaseDto {

    private String repentanceDate;

    private String baptismDate;

    private String type;

    private String ordinationDate;

    private String churchParticipation;

}
