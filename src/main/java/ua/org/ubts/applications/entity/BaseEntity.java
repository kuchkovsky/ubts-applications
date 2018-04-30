package ua.org.ubts.applications.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity<I> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private I id;

}
