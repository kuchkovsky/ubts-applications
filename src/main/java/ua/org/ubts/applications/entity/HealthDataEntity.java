package ua.org.ubts.applications.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "health_data")
@Getter
@Setter
@NoArgsConstructor
public class HealthDataEntity extends BaseEntity<Long> {

    @Column(name = "is_drug_addicted")
    private Boolean drugAddicted;

    @Column(name = "had_drug_or_alcohol_addiction", nullable = false)
    private Boolean hadDrugOrAlcoholAddiction;

    @Column(name = "had_gambling_or_computer_addiction", nullable = false)
    private Boolean hadGamblingOrComputerAddiction;

    @Column(name = "had_occult_addiction", nullable = false)
    private Boolean hadOccultAddiction;

    @NotEmpty
    @Column(name = "health_status", nullable = false, length = 32)
    private String healthStatus;

    @Column(name = "is_taking_medicine", nullable = false)
    private Boolean takingMedicine;

    @Column(name = "has_study_problems", nullable = false)
    private Boolean hasStudyProblems;

    @OneToOne(mappedBy = "healthData")
    private StudentEntity student;

}
