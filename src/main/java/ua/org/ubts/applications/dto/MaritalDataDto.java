package ua.org.ubts.applications.dto;

import lombok.Data;

@Data
public class MaritalDataDto extends BaseDto {

    private String status;

    private String spouseName;

    private String marriageDate;

    private Boolean spouseChurchMember;

    private String spouseChurchMinistry;

    private Integer childrenNumber;

    private Boolean spouseApproveSeminary;

    public Boolean isSpouseChurchMember() {
        return spouseChurchMember;
    }

    public void setIsSpouseChurchMember(Boolean spouseChurchMember) {
        this.spouseChurchMember = spouseChurchMember;
    }

    public Boolean isSpouseApproveSeminary() {
        return spouseApproveSeminary;
    }

    public void setIsSpouseApproveSeminary(Boolean spouseApproveSeminary) {
        this.spouseApproveSeminary = spouseApproveSeminary;
    }

}
