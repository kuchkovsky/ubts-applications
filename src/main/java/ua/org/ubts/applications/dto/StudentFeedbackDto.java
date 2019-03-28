package ua.org.ubts.applications.dto;

import lombok.Data;

@Data
public class StudentFeedbackDto extends BaseDto {

    private String howLongKnowEachOther;

    private String howWellKnowStudent;

    private String jesusDedication;

    private String strongCharacterTraits;

    private String extraordinaryTalents;

    private String nonCanonicalHabits;

    private String friendsAndChurchAttitude;

    private String churchManagementRelationship;

    private String possibleNegativeSpecialCircumstances;

    private StudentTraitsDto studentTraits;

    private String personalRecommendation;

    private String whyBanThisStudent;

    private String name;

    private String position;

    private String address;

    private String email;

    private Boolean readyForMoreInformation;

}
