package ua.org.ubts.applications.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "friend_feedback")
@Getter
@Setter
@NoArgsConstructor
public class FriendFeedbackEntity extends StudentFeedbackEntity {

    @NotEmpty
    @Column(name = "areas_that_need_development", nullable = false, columnDefinition = "TEXT")
    private String areasThatNeedDevelopment;

    @NotEmpty
    @Column(name = "church_ministry_area", nullable = false, columnDefinition = "TEXT")
    private String churchMinistryArea;

    @NotEmpty
    @Column(name = "phone", nullable = false)
    private String phone;

}
