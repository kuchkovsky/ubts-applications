package ua.org.ubts.applications.dto;

import lombok.Data;

@Data
public class HealthDataDto extends BaseDto {

    private Boolean drugAddicted;

    private String healthStatus;

    private Boolean takingMedicine;

    private Boolean hasStudyProblems;

    public Boolean isDrugAddicted() {
        return drugAddicted;
    }

    public Boolean isTakingMedicine() {
        return takingMedicine;
    }

    public void setIsDrugAddicted(Boolean drugAddicted) {
        this.drugAddicted = drugAddicted;
    }

    public void setIsTakingMedicine(Boolean takingMedicine) {
        this.takingMedicine = takingMedicine;
    }

}
