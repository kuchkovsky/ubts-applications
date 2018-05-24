package ua.org.ubts.applications.dto;

import lombok.Data;

@Data
public class HealthDataDto extends BaseDto {

    private Boolean isDrugAddicted;

    private String healthStatus;

    private Boolean isTakingMedicine;

    private Boolean hasStudyProblems;

}
