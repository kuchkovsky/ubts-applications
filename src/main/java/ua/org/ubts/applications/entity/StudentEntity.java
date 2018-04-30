package ua.org.ubts.applications.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "student")
@Getter
@Setter
@NoArgsConstructor
public class StudentEntity extends PersonEntity {

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "program_id")
    private ProgramEntity program;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "entry_year_id")
    private YearEntity entryYear;

    @NotEmpty
    @Column(name = "donation_amount", nullable = false, length = 16)
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
    @Column(name = "how_came_to_god", nullable = false, columnDefinition = "TEXT")
    private String howCameToGod;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "how_find_out_id")
    private HowFindOutEntity howFindOut;

    @Column(name = "has_files_uploaded")
    private Boolean filesUploaded;

}
