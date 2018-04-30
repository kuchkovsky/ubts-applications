package ua.org.ubts.applications.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "church_ministry")
@Getter
@Setter
@NoArgsConstructor
public class ChurchMinistryEntity extends BaseEntity<Long> {

    @NotEmpty
    @Column(name = "repentance_date", nullable = false, length = 12)
    private String repentanceDate;

    @NotEmpty
    @Column(name = "baptism_date", nullable = false, length = 12)
    private String baptismDate;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "type_id")
    private ChurchMinistryTypeEntity type;

    @Column(name = "ordination_date", length = 12)
    private String ordinationDate;

    @NotEmpty
    @Column(name = "church_participation", nullable = false, columnDefinition = "TEXT")
    private String churchParticipation;

    @OneToOne(mappedBy = "churchMinistry")
    private StudentEntity student;

}
