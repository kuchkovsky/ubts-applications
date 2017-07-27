package ua.org.ubts.applicationssystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "marital_status")
public class MaritalStatus implements Serializable {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(name = "name", nullable = false, length = 32)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "status")
    private List<MaritalData> maritalDataList;

    public MaritalStatus() {}

    public MaritalStatus(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MaritalStatus{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", maritalDataList=" + maritalDataList +
                '}';
    }

}
