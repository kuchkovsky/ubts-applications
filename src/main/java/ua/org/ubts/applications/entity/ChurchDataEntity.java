package ua.org.ubts.applications.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "church_data")
@Getter
@Setter
@NoArgsConstructor
public class ChurchDataEntity extends BaseEntity<Long> {

    @NotEmpty
    @Column(name = "name", nullable = false)
    private String name;

    @NotEmpty
    @Column(name = "pastor_name", nullable = false)
    private String pastorName;

    @NotEmpty
    @Column(name = "union_", nullable = false)
    private String union;

    @NotEmpty
    @Column(name = "denomination", nullable = false)
    private String denomination;

    @NotEmpty
    @Column(name = "members_number", nullable = false, length = 32)
    private String membersNumber;

    @NotEmpty
    @Column(name = "region", nullable = false, length = 128)
    private String region;

    @NotEmpty
    @Column(name = "city_village", nullable = false, length = 128)
    private String cityVillage;

    @NotEmpty
    @Column(name = "index_", nullable = false, length = 16)
    private String index;

    @Column(name = "district", length = 128)
    private String district;

    @NotEmpty
    @Column(name = "street_and_house_number", nullable = false, length = 192)
    private String streetAndHouseNumber;

    @Column(name = "phone", length = 32)
    private String phone;

    @OneToOne(mappedBy = "churchData")
    private StudentEntity student;

}
