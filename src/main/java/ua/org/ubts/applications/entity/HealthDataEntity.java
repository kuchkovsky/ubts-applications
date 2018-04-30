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

    @Column(name = "is_drug_addicted", nullable = false)
    private Boolean drugAddicted;

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
