package ua.org.ubts.applications.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "student")
@Getter
@Setter
@NoArgsConstructor
public class StudentEntity extends PersonEntity {

    @Column(name = "uuid")
    private String uuid;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "program_id")
    private ProgramEntity program;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "entry_year_id")
    private YearEntity entryYear;

    @Column(name = "donation_amount", length = 16)
    private String donationAmount;

    @Column(name = "finance_comments", columnDefinition = "TEXT")
    private String financeComments;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "health_data_id")
    private HealthDataEntity healthData;

    @NotEmpty
    @Column(name = "reasons_to_study", nullable = false, columnDefinition = "TEXT")
    private String reasonsToStudy;

    @NotEmpty
    @Column(name = "study_expectations", nullable = false, columnDefinition = "TEXT")
    private String studyExpectations;

    @NotEmpty
    @Column(name = "how_came_to_god", nullable = false, columnDefinition = "TEXT")
    private String howCameToGod;

    @NotEmpty
    @Column(name = "mostDifficultBibleTheme", nullable = false, columnDefinition = "TEXT")
    private String mostDifficultBibleTheme;

    @NotEmpty
    @Column(name = "answers", nullable = false, columnDefinition = "TEXT")
    private String answers;

    @NotEmpty
    @Column(name = "lastVerseFromBible", nullable = false, columnDefinition = "TEXT")
    private String lastVerseFromBible;

    @NotEmpty
    @Column(name = "lastInterestingPreaching", nullable = false, columnDefinition = "TEXT")
    private String lastInterestingPreaching;

    @NotEmpty
    @Column(name = "didYouReadBible", nullable = false, columnDefinition = "TEXT")
    private String didYouReadBible;

    @NotEmpty
    @Column(name = "prayForSomeone", nullable = false, columnDefinition = "TEXT")
    private String prayForSomeone;

    @NotEmpty
    @Column(name = "missions", nullable = false, columnDefinition = "TEXT")
    private String missions;

    @NotEmpty
    @Column(name = "whenYouToldAboutChrist", nullable = false, columnDefinition = "TEXT")
    private String whenYouToldAboutChrist;

    @NotEmpty
    @Column(name = "spiritGifts", nullable = false, columnDefinition = "TEXT")
    private String spiritGifts;

    @NotEmpty
    @Column(name = "bestInChurch", nullable = false, columnDefinition = "TEXT")
    private String bestInChurch;

    @NotEmpty
    @Column(name = "mentor", nullable = false, columnDefinition = "TEXT")
    private String mentor;

    @NotEmpty
    @Column(name = "mainInfluencer", nullable = false, columnDefinition = "TEXT")
    private String mainInfluencer;

    @NotEmpty
    @Column(name = "testScore", nullable = false, columnDefinition = "TEXT")
    private String testScore;

    @NotEmpty
    @Column(name = "testTime", nullable = false, columnDefinition = "TEXT")
    private String testTime;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "how_find_out_id")
    private HowFindOutEntity howFindOut;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "pastor_feedback_id")
    private PastorFeedbackEntity pastorFeedback;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "friend_feedback1_id")
    private FriendFeedbackEntity friendFeedback1;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "friend_feedback2_id")
    private FriendFeedbackEntity friendFeedback2;

    @Column(name = "pastor_feedback_uploaded")
    private Boolean pastorFeedbackUploaded;

    @Column(name = "friend_feedback_uploaded")
    private Boolean friendFeedbackUploaded;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "student_question",
            joinColumns = @JoinColumn(
                    name = "student_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "question_id", referencedColumnName = "id"))
    private List<QuestionEntity> questions;
}
