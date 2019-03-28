package ua.org.ubts.applications.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "pastor_feedback")
@Getter
@Setter
@NoArgsConstructor
public class PastorFeedbackEntity extends StudentFeedbackEntity {

    @NotEmpty
    @Column(name = "how_long_is_church_member", nullable = false)
    private String howLongIsChurchMember;

    @NotEmpty
    @Column(name = "church_participation_level", nullable = false)
    private String churchParticipationLevel;

    @NotEmpty
    @Column(name = "church_ministry_type_and_term", nullable = false, columnDefinition = "TEXT")
    private String churchMinistryTypeAndTerm;

    @NotEmpty
    @Column(name = "spiritual_influence", nullable = false)
    private String spiritualInfluence;

    @NotEmpty
    @Column(name = "study_expectations", nullable = false, columnDefinition = "TEXT")
    private String studyExpectations;

    @NotEmpty
    @Column(name = "donation_info", nullable = false)
    private String donationInfo;

    @Column(name = "ready_for_special_meeting", nullable = false)
    private Boolean readyForSpecialMeeting;

    @NotEmpty
    @Column(name = "church_name", nullable = false)
    private String churchName;

    @NotEmpty
    @Column(name = "church_address", nullable = false)
    private String churchAddress;

    @NotEmpty
    @Column(name = "church_phone", nullable = false)
    private String churchPhone;

}
