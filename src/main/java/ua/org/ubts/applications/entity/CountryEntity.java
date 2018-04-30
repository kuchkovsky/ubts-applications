package ua.org.ubts.applications.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "country")
@Getter
@Setter
@NoArgsConstructor
public class CountryEntity extends BaseEntity<Integer> {

    @NotEmpty
    @Column(name = "name", nullable = false, length = 128)
    private String name;

    @OneToMany(mappedBy = "country")
    private List<ResidenceEntity> residences;

}
