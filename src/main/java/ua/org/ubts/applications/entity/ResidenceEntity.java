package ua.org.ubts.applications.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "residence")
@Getter
@Setter
@NoArgsConstructor
public class ResidenceEntity extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "country_id")
    private CountryEntity country;

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

    @OneToOne(mappedBy = "residence")
    private StudentEntity student;

}
