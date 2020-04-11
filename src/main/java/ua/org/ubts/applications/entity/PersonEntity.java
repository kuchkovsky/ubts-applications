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
public class PersonEntity extends BaseEntity<Long> {

    @NotEmpty
    @Column(name = "first_name", nullable = false, length = 64)
    private String firstName;

    @NotEmpty
    @Column(name = "middle_name", nullable = false, length = 64)
    private String middleName;

    @NotEmpty
    @Column(name = "last_name", nullable = false, length = 64)
    private String lastName;

    @Column(name = "international_first_name", length = 64)
    private String internationalFirstName;

    @Column(name = "international_last_name", length = 64)
    private String internationalLastName;

    @NotEmpty
    @Column(name = "birthdate", nullable = false, length = 12)
    private String birthDate;

    @NotEmpty
    @Column(name = "phone1", nullable = false, length = 32)
    private String phone1;

    @Column(name = "phone2", length = 32)
    private String phone2;

    @NotEmpty
    @Column(name = "email", nullable = false, length = 96)
    private String email;

    @Column(name = "job", length = 128)
    private String job;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "residence_id")
    private ResidenceEntity residence;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "education_id")
    private EducationEntity education;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "marital_data_id")
    private MaritalDataEntity maritalData;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "church_data_id")
    private ChurchDataEntity churchData;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "church_ministry_id")
    private ChurchMinistryEntity churchMinistry;

    public String getFullSlavicName() {
        return lastName + " " + firstName + " " + middleName;
    }

}
