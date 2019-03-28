package ua.org.ubts.applications.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "student_traits")
@Getter
@Setter
@NoArgsConstructor
public class StudentTraitsEntity extends BaseEntity<Long> {

    @NotEmpty
    @Column(name = "respect_for_elderly", nullable = false)
    private String respectForElderly;

    @NotEmpty
    @Column(name = "honesty_and_openness", nullable = false)
    private String honestyAndOpenness;

    @NotEmpty
    @Column(name = "goodwill", nullable = false)
    private String goodwill;

    @NotEmpty
    @Column(name = "vulnerability", nullable = false)
    private String vulnerability;

    @NotEmpty
    @Column(name = "ministry_responsibility", nullable = false)
    private String ministryResponsibility;

    @NotEmpty
    @Column(name = "do_own_way_desire", nullable = false)
    private String doOwnWayDesire;

    @NotEmpty
    @Column(name = "completes_the_business", nullable = false)
    private String completesTheBusiness;

    @NotEmpty
    @Column(name = "respect_others_opinions", nullable = false)
    private String respectOthersOpinions;

    @NotEmpty
    @Column(name = "negative_feedback_reaction", nullable = false)
    private String negativeFeedbackReaction;

    @NotEmpty
    @Column(name = "teach_others", nullable = false)
    private String teachOthers;

    @NotEmpty
    @Column(name = "lead_others", nullable = false)
    private String leadOthers;

    @NotEmpty
    @Column(name = "neatness", nullable = false)
    private String neatness;

    @NotEmpty
    @Column(name = "general_knowledge", nullable = false)
    private String generalKnowledge;

    @NotEmpty
    @Column(name = "finance_skill", nullable = false)
    private String financeSkill;

    @NotEmpty
    @Column(name = "family_management_skill", nullable = false)
    private String familyManagementSkill;

    @NotEmpty
    @Column(name = "get_new_friends_skill", nullable = false)
    private String getNewFriendsSkill;

    @Column(name = "emotional_stability")
    private String emotionalStability;

}
