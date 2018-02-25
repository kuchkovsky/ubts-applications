package ua.org.ubts.applicationssystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.org.ubts.applicationssystem.entity.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {

    Property findByKey(String key);

}
