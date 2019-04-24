package ua.org.ubts.applications.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public class StudentFeedbackEntity extends BaseEntity<Long> {

    @NotEmpty
    @Column(name = "how_long_know_each_other", nullable = false, columnDefinition = "TEXT")
    private String howLongKnowEachOther;

    @NotEmpty
    @Column(name = "how_well_know_student", nullable = false, columnDefinition = "TEXT")
    private String howWellKnowStudent;

    @NotEmpty
    @Column(name = "jesus_dedication", nullable = false, columnDefinition = "TEXT")
    private String jesusDedication;

    @NotEmpty
    @Column(name = "strong_character_traits", nullable = false, columnDefinition = "TEXT")
    private String strongCharacterTraits;

    @NotEmpty
    @Column(name = "extraordinary_talents", nullable = false, columnDefinition = "TEXT")
    private String extraordinaryTalents;

    @NotEmpty
    @Column(name = "non_canonical_habits", nullable = false, columnDefinition = "TEXT")
    private String nonCanonicalHabits;

    @NotEmpty
    @Column(name = "friends_and_church_attitude", nullable = false, columnDefinition = "TEXT")
    private String friendsAndChurchAttitude;

    @Column(name = "church_management_relationship")
    private String churchManagementRelationship;

    @NotEmpty
    @Column(name = "possible_negative_special_circumstances", nullable = false, columnDefinition = "TEXT")
    private String possibleNegativeSpecialCircumstances;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "student_traits_id")
    private StudentTraitsEntity studentTraits;

    @NotEmpty
    @Column(name = "personal_recommendation", nullable = false)
    private String personalRecommendation;

    @NotEmpty
    @Column(name = "why_ban_this_student", nullable = false, columnDefinition = "TEXT")
    private String whyBanThisStudent;

    @NotEmpty
    @Column(name = "name", nullable = false)
    private String name;

    @NotEmpty
    @Column(name = "position", nullable = false)
    private String position;

    @NotEmpty
    @Column(name = "address", nullable = false)
    private String address;

    @NotEmpty
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "ready_for_more_information", nullable = false)
    private Boolean readyForMoreInformation;

}
