package ua.org.ubts.applications.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "marital_data")
@Getter
@Setter
@NoArgsConstructor
public class MaritalDataEntity extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "status_id")
    private MaritalStatusEntity status;

    @Column(name = "spouse_name")
    private String spouseName;

    @Column(name = "marriage_date", length = 12)
    private String marriageDate;

    @Column(name = "is_spouse_church_member")
    private Boolean spouseChurchMember;

    @Column(name = "spouse_church_ministry", columnDefinition="TEXT")
    private String spouseChurchMinistry;

    @Column(name = "children_number")
    private Integer childrenNumber;

    @Column(name = "is_spouse_approve_seminary")
    private Boolean spouseApproveSeminary;

    @OneToOne(mappedBy = "maritalData")
    private StudentEntity student;

}
