package ua.org.ubts.applications.dto;

import lombok.Data;

@Data
public class StudentFilesDto extends BaseDto {

    private String photo;

    private String passport1;

    private String passport2;

    private String passport3;

    private String idNumber;

    private String diploma;

    private String supplement1;

    private String supplement2;

    private String medicalReference1;

    private String medicalReference2;

}
