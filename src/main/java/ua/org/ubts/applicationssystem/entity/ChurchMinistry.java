package ua.org.ubts.applicationssystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.validator.constraints.NotEmpty;
import ua.org.ubts.applicationssystem.serialize.ChurchMinistrySerializer;

import javax.persistence.*;
import java.io.Serializable;

@JsonSerialize(using = ChurchMinistrySerializer.class)
@Entity
@Table(name = "church_ministry")
public class ChurchMinistry implements Serializable {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(name = "repentance_date", nullable = false, length = 12)
    private String repentanceDate;

    @NotEmpty
    @Column(name = "baptism_date", nullable = false, length = 12)
    private String baptismDate;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "type")
    private ChurchMinistryType type;

    @Column(name = "ordination_date", length = 12)
    private String ordinationDate;

    @NotEmpty
    @Column(name = "church_participation", nullable = false, columnDefinition="TEXT")
    private String churchParticipation;

    @JsonIgnore
    @OneToOne(mappedBy = "churchMinistry")
    private Student student;

    public ChurchMinistry() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRepentanceDate() {
        return repentanceDate;
    }

    public void setRepentanceDate(String repentanceDate) {
        this.repentanceDate = repentanceDate;
    }

    public String getBaptismDate() {
        return baptismDate;
    }

    public void setBaptismDate(String baptismDate) {
        this.baptismDate = baptismDate;
    }

    public ChurchMinistryType getType() {
        return type;
    }

    public void setType(ChurchMinistryType type) {
        this.type = type;
    }

    public String getOrdinationDate() {
        return ordinationDate;
    }

    public void setOrdinationDate(String ordinationDate) {
        this.ordinationDate = ordinationDate;
    }

    public String getChurchParticipation() {
        return churchParticipation;
    }

    public void setChurchParticipation(String churchParticipation) {
        this.churchParticipation = churchParticipation;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "ChurchMinistry{" +
                "id=" + id +
                ", repentanceDate='" + repentanceDate + '\'' +
                ", baptismDate='" + baptismDate + '\'' +
                ", type='" + type + '\'' +
                ", ordinationDate='" + ordinationDate + '\'' +
                ", churchParticipation='" + churchParticipation + '\'' +
                '}';
    }

}
