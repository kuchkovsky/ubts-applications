package ua.org.ubts.applicationssystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Yaroslav on 14.07.2017.
 */

@MappedSuperclass
public class Person implements Serializable {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(name = "first_name", nullable = false, length = 64)
    private String firstName;

    @NotEmpty
    @Column(name = "middle_name", nullable = false, length = 64)
    private String middleName;

    @NotEmpty
    @Column(name = "last_name", nullable = false, length = 64)
    private String lastName;

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
    private Residence residence;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "education_id")
    private Education education;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "marital_data_id")
    private MaritalData maritalData;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "church_data_id")
    private ChurchData churchData;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "church_ministry_id")
    private ChurchMinistry churchMinistry;

    public Person() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonIgnore
    public String getFullName() {
        return firstName + " " + middleName + " " + lastName;
    }

    @JsonIgnore
    public String getFullSlavicName() {
        return lastName + " " + firstName + " " + middleName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Residence getResidence() {
        return residence;
    }

    public void setResidence(Residence residence) {
        this.residence = residence;
    }

    public Education getEducation() {
        return education;
    }

    public void setEducation(Education education) {
        this.education = education;
    }

    public MaritalData getMaritalData() {
        return maritalData;
    }

    public void setMaritalData(MaritalData maritalData) {
        this.maritalData = maritalData;
    }

    public ChurchData getChurchData() {
        return churchData;
    }

    public void setChurchData(ChurchData churchData) {
        this.churchData = churchData;
    }

    public ChurchMinistry getChurchMinistry() {
        return churchMinistry;
    }

    public void setChurchMinistry(ChurchMinistry churchMinistry) {
        this.churchMinistry = churchMinistry;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", phone1='" + phone1 + '\'' +
                ", phone2='" + phone2 + '\'' +
                ", email='" + email + '\'' +
                ", job='" + job + '\'' +
                ", residence=" + residence +
                ", education=" + education +
                ", maritalData=" + maritalData +
                ", churchData=" + churchData +
                ", churchMinistry=" + churchMinistry +
                '}';
    }

}
