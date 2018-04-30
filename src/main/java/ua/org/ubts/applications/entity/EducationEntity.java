package ua.org.ubts.applications.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "education")
@Getter
@Setter
@NoArgsConstructor
public class EducationEntity extends BaseEntity<Integer> {

    @NotEmpty
    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @OneToMany(mappedBy = "education")
    private List<StudentEntity> students;

}
