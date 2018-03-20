package ua.org.ubts.applicationssystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "how_find_out")
public class HowFindOut {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(name = "name", nullable = false, length = 128)
    private String name;

    public HowFindOut() {}

    public HowFindOut(String name) {
        this.name = name;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "howFindOut")
    private List<Student> students;

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

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "HowFindOut{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
