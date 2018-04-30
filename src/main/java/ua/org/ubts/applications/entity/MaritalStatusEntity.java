package ua.org.ubts.applications.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "marital_status")
@Getter
@Setter
@NoArgsConstructor
public class MaritalStatusEntity extends BaseEntity<Integer> {

    @NotEmpty
    @Column(name = "name", nullable = false, length = 32)
    private String name;

    @OneToMany(mappedBy = "status")
    private List<MaritalDataEntity> maritalDataList;

}
