package ua.org.ubts.applications.dto;

import lombok.Data;

import java.util.List;

@Data
public class StudentDto extends BaptistDto {

    private String id;

    private ProgramDto program;

    private YearDto entryYear;

    private String donationAmount;

    private String financeComments;

    private HealthDataDto healthData;

    private String reasonsToStudy;

    private String studyExpectations;

    private String howCameToGod;

    private String howFindOut;

    private StudentFilesDto files;

    private PastorFeedbackDto pastorFeedback;

    private FriendFeedbackDto friendFeedback1;

    private FriendFeedbackDto friendFeedback2;

    private List<String> fileNames;

}
