package ua.org.ubts.applications.dto;

import lombok.Data;

@Data
public class MaritalDataDto extends BaseDto {

    private String status;

    private String spouseName;

    private String marriageDate;

    private Boolean isSpouseChurchMember;

    private String spouseChurchMinistry;

    private Integer childrenNumber;

    private Boolean isSpouseApproveSeminary;

}
