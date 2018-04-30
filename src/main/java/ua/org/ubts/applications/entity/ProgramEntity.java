package ua.org.ubts.applications.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "program")
@Getter
@Setter
@NoArgsConstructor
public class ProgramEntity extends BaseEntity<Integer> {

    @NotEmpty
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "info", length = 64)
    private String info;

    @Column(name = "abbreviation", length = 64)
    private String abbreviation;

    @OneToMany(mappedBy = "program")
    private List<StudentEntity> students;

}
