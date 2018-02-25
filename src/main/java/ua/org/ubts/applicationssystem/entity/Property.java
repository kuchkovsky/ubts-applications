package ua.org.ubts.applicationssystem.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "property")
public class Property implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(name = "key_", nullable = false)
    private String key;

    @Column(name = "value")
    private String value;

    @Column(name = "boolean_value")
    private Boolean booleanValue;

    public Property() {}

    public Property(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public Property(String key, Boolean booleanValue) {
        this.key = key;
        this.booleanValue = booleanValue;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(Boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    @Override
    public String toString() {
        return "Property{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", booleanValue=" + booleanValue +
                '}';
    }

}
