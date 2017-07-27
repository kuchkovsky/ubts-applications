package ua.org.ubts.applicationssystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "health_data")
public class HealthData implements Serializable {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "is_drug_addicted", nullable = false)
    private Boolean drugAddicted;

    @NotEmpty
    @Column(name = "health_status", nullable = false, length = 32)
    private String healthStatus;

    @Column(name = "is_taking_medicine", nullable = false)
    private Boolean takingMedicine;

    @Column(name = "has_study_problems", nullable = false)
    private Boolean hasStudyProblems;

    public HealthData() {}

    public HealthData(Boolean drugAddicted, String healthStatus, Boolean takingMedicine, Boolean hasStudyProblems) {
        this.drugAddicted = drugAddicted;
        this.healthStatus = healthStatus;
        this.takingMedicine = takingMedicine;
        this.hasStudyProblems = hasStudyProblems;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty(value = "isDrugAddicted")
    public Boolean getDrugAddicted() {
        return drugAddicted;
    }

    @JsonProperty(value = "isDrugAddicted")
    public void setDrugAddicted(Boolean drugAddicted) {
        this.drugAddicted = drugAddicted;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    @JsonProperty(value = "isTakingMedicine")
    public Boolean getTakingMedicine() {
        return takingMedicine;
    }

    @JsonProperty(value = "isTakingMedicine")
    public void setTakingMedicine(Boolean takingMedicine) {
        this.takingMedicine = takingMedicine;
    }

    public Boolean getHasStudyProblems() {
        return hasStudyProblems;
    }

    public void setHasStudyProblems(Boolean hasStudyProblems) {
        this.hasStudyProblems = hasStudyProblems;
    }

    @Override
    public String toString() {
        return "HealthData{" +
                "id=" + id +
                ", drugAddicted=" + drugAddicted +
                ", healthStatus='" + healthStatus + '\'' +
                ", takingMedicine=" + takingMedicine +
                ", hasStudyProblems=" + hasStudyProblems +
                '}';
    }

}
