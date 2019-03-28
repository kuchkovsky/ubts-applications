package ua.org.ubts.applications.dto;

import lombok.Data;

@Data
public class PastorFeedbackDto extends StudentFeedbackDto {

    private String howLongIsChurchMember;

    private String churchParticipationLevel;

    private String churchMinistryTypeAndTerm;

    private String spiritualInfluence;

    private String studyExpectations;

    private String donationInfo;

    private Boolean readyForSpecialMeeting;

    private String churchName;

    private String churchAddress;

    private String churchPhone;

}
