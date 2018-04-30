package ua.org.ubts.applications.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "year")
@Getter
@Setter
@NoArgsConstructor
public class YearEntity extends BaseEntity<Integer> {

    @Column(name = "value", nullable = false)
    private Integer value;

    @OneToMany(mappedBy = "entryYear")
    private List<StudentEntity> students;

}
