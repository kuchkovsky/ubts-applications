package ua.org.ubts.applications.dto;

import lombok.Data;

@Data
public class ChurchDataDto extends BaseDto {

    private String name;

    private String pastorName;

    private String union;

    private String denomination;

    private String membersNumber;

    private String region;

    private String cityVillage;

    private String index;

    private String district;

    private String streetAndHouseNumber;

    private String phone;

}
