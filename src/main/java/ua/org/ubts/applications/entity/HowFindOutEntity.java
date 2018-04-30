package ua.org.ubts.applications.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "how_find_out")
@Getter
@Setter
@NoArgsConstructor
public class HowFindOutEntity extends BaseEntity<Integer> {

    @NotEmpty
    @Column(name = "name", nullable = false, length = 128)
    private String name;

    @OneToMany(mappedBy = "howFindOut")
    private List<StudentEntity> students;

}
