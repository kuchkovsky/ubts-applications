package ua.org.ubts.applications.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.org.ubts.applications.entity.ChurchMinistryTypeEntity;

import java.util.Optional;

@Repository
public interface ChurchMinistryTypeRepository extends JpaRepository<ChurchMinistryTypeEntity, Integer> {

    Optional<ChurchMinistryTypeEntity> findByName(String name);

}
