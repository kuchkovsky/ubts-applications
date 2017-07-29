package ua.org.ubts.applicationssystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.validator.constraints.NotEmpty;
import ua.org.ubts.applicationssystem.serialize.StudentSerializer;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Yaroslav on 17.07.2017.
 */

@JsonSerialize(using = StudentSerializer.class)
@Entity
@Table(name = "student")
public class Student extends Person implements Serializable {

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "program")
    private Program program;

    @NotEmpty
    @Column(name = "donation_amount", nullable = false, length = 16)
    private String donationAmount;

    @Column(name = "finance_comments", columnDefinition="TEXT")
    private String financeComments;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "health_data")
    private HealthData healthData;

    @NotEmpty
    @Column(name = "reasons_to_study", nullable = false, columnDefinition="TEXT")
    private String reasonsToStudy;

    @NotEmpty
    @Column(name = "how_came_to_god", nullable = false, columnDefinition="TEXT")
    private String howCameToGod;

    @JsonIgnore
    @Column(name = "has_files_uploaded")
    private Boolean filesUploaded;

    public Student() {}

    public Student(String firstName, String middleName, String lastName, String birthDate, String phone1, String phone2,
                   String email, Program program) {
        super(firstName, middleName, lastName, birthDate, phone1, phone2, email);
        this.program = program;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public String getDonationAmount() {
        return donationAmount;
    }

    public void setDonationAmount(String donationAmount) {
        this.donationAmount = donationAmount;
    }

    public String getFinanceComments() {
        return financeComments;
    }

    public void setFinanceComments(String financeComments) {
        this.financeComments = financeComments;
    }

    public HealthData getHealthData() {
        return healthData;
    }

    public void setHealthData(HealthData healthData) {
        this.healthData = healthData;
    }

    public String getReasonsToStudy() {
        return reasonsToStudy;
    }

    public void setReasonsToStudy(String reasonsToStudy) {
        this.reasonsToStudy = reasonsToStudy;
    }

    public String getHowCameToGod() {
        return howCameToGod;
    }

    public void setHowCameToGod(String howCameToGod) {
        this.howCameToGod = howCameToGod;
    }

    public Boolean hasFilesUploaded() {
        return filesUploaded;
    }

    public void setFilesUploaded(Boolean filesUploaded) {
        this.filesUploaded = filesUploaded;
    }

    @Override
    public String toString() {
        String string = "Student{" +
                "firstName='" + getFirstName() + '\'' +
                ", middleName='" + getMiddleName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", birthDate=" + getBirthDate() +
                ", phone1='" + getPhone1() + '\'' +
                ", phone2='" + getPhone2() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", program='" + program + '\'' +
                ", donationAmount='" + donationAmount + '\'' +
                '}' + "\n" + getResidence().toString() + "\n";
        if (getChurchData() != null) {
            string += getChurchData().toString() + "\n";
        }
        if (getChurchMinistry() != null) {
            string += getChurchMinistry().toString() + "\n";
        }
        string += healthData + "\n";
        return string;
    }

}
