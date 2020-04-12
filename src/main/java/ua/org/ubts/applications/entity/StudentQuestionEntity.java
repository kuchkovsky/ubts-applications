package ua.org.ubts.applications.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "student_question")
@Getter
@Setter
@NoArgsConstructor
public class StudentQuestionEntity extends BaseEntity<Long>{
    @Column(name = "answer")
    private String answer;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id")
    private StudentEntity student;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id")
    private QuestionEntity question;
}
