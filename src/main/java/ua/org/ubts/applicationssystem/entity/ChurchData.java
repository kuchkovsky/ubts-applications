package ua.org.ubts.applicationssystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "church_data")
public class ChurchData implements Serializable {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "district", length = 128)
    private String district;

    @NotEmpty
    @Column(name = "street_and_house_number", nullable = false, length = 192)
    private String streetAndHouseNumber;

    @Column(name = "phone", length = 32)
    private String phone;

    @JsonIgnore
    @OneToOne(mappedBy = "churchData")
    private Student student;

    public ChurchData() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPastorName() {
        return pastorName;
    }

    public void setPastorName(String pastorName) {
        this.pastorName = pastorName;
    }

    public String getUnion() {
        return union;
    }

    public void setUnion(String union) {
        this.union = union;
    }

    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public String getMembersNumber() {
        return membersNumber;
    }

    public void setMembersNumber(String membersNumber) {
        this.membersNumber = membersNumber;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCityVillage() {
        return cityVillage;
    }

    public void setCityVillage(String cityVillage) {
        this.cityVillage = cityVillage;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreetAndHouseNumber() {
        return streetAndHouseNumber;
    }

    public void setStreetAndHouseNumber(String streetAndHouseNumber) {
        this.streetAndHouseNumber = streetAndHouseNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "ChurchData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pastorName='" + pastorName + '\'' +
                ", union='" + union + '\'' +
                ", denomination='" + denomination + '\'' +
                ", membersNumber='" + membersNumber + '\'' +
                ", region='" + region + '\'' +
                ", cityVillage='" + cityVillage + '\'' +
                ", index='" + index + '\'' +
                ", district='" + district + '\'' +
                ", streetAndHouseNumber='" + streetAndHouseNumber + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

}
