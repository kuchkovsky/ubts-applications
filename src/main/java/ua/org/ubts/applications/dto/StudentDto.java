package ua.org.ubts.applications.dto;

import lombok.Data;

@Data
public class StudentDto extends BaptistDto {

    private ProgramDto program;

    private YearDto entryYear;

    private String donationAmount;

    private String financeComments;

    private HealthDataDto healthData;

    private String reasonsToStudy;

    private String howCameToGod;

    private String howFindOut;

}
