package ua.org.ubts.applicationssystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;


import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "program")
public class Program implements Serializable {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(name = "name", nullable = false)
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "info", length = 64)
    private String info;

    @Column(name = "abbreviation", length = 64)
    private String abbreviation;

    @JsonIgnore
    @OneToMany(mappedBy = "program")
    private List<Student> students;

    public Program() {}

    public Program(String name, String info) {
        this.name = name;
        this.info = info;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    @Override
    public String toString() {
        return "Program{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", info='" + info + '\'' +
                '}';
    }

}
