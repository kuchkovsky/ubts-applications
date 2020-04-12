package ua.org.ubts.applications.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "question")
@Getter
@Setter
@NoArgsConstructor
public class QuestionEntity extends BaseEntity<Long>{
    @Column(name = "text")
    private String text;

    @ManyToMany(mappedBy = "questions")
    private List<StudentEntity> students;
}