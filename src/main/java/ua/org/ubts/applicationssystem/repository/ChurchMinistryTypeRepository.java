package ua.org.ubts.applicationssystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.org.ubts.applicationssystem.entity.ChurchMinistryType;

@Repository
public interface ChurchMinistryTypeRepository extends JpaRepository<ChurchMinistryType, Integer> {

    ChurchMinistryType findByName(String name);

}
