package ua.org.ubts.applicationssystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.validator.constraints.NotEmpty;
import ua.org.ubts.applicationssystem.serialize.ResidenceSerializer;


import javax.persistence.*;
import java.io.Serializable;

@JsonSerialize(using = ResidenceSerializer.class)
@Entity
@Table(name = "residence")
public class Residence implements Serializable {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "country")
    private Country country;

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
    @Column(name = "street", nullable = false, length = 128)
    private String street;

    @NotEmpty
    @Column(name = "house", nullable = false, length = 16)
    private String house;

    @Column(name = "apartment", length = 16)
    private String apartment;

    public Residence() {}

    public Residence(Country country, String region, String cityVillage, String index, String district, String street,
                     String house, String apartment) {
        this.country = country;
        this.region = region;
        this.cityVillage = cityVillage;
        this.index = index;
        this.district = district;
        this.street = street;
        this.house = house;
        this.apartment = apartment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    @Override
    public String toString() {
        return "Residence{" +
                "id=" + id +
                ", country=" + country +
                ", region='" + region + '\'' +
                ", cityVillage='" + cityVillage + '\'' +
                ", index='" + index + '\'' +
                ", district='" + district + '\'' +
                ", street='" + street + '\'' +
                ", house='" + house + '\'' +
                ", apartment='" + apartment + '\'' +
                '}';
    }

}
