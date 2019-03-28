package ua.org.ubts.applications.dto;

import lombok.Data;

@Data
public class FriendFeedbackDto extends StudentFeedbackDto {

    private String areasThatNeedDevelopment;

    private String churchMinistryArea;

    private String phone;

}
