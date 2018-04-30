package ua.org.ubts.applications.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "property")
@Getter
@Setter
@NoArgsConstructor
public class BooleanPropertyEntity extends BaseEntity<Integer> {

    @NotEmpty
    @Column(name = "key_", nullable = false)
    private String key;

    @Column(name = "value")
    private Boolean value;

    public BooleanPropertyEntity(@NotEmpty String key) {
        this.key = key;
    }

}
