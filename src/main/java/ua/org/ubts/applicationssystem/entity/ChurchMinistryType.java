package ua.org.ubts.applicationssystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "church_ministry_type")
public class ChurchMinistryType implements Serializable {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(name = "name", nullable = false, length = 128)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "type")
    private List<ChurchMinistry> churchMinistryList;

    public ChurchMinistryType() {}

    public ChurchMinistryType(String name) {
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

    public List<ChurchMinistry> getChurchMinistryList() {
        return churchMinistryList;
    }

    public void setChurchMinistryList(List<ChurchMinistry> churchMinistryList) {
        this.churchMinistryList = churchMinistryList;
    }

    @Override
    public String toString() {
        return "ChurchMinistryType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
