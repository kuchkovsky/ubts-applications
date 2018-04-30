package ua.org.ubts.applications.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.org.ubts.applications.entity.EducationEntity;

import java.util.Optional;

@Repository
public interface EducationRepository extends JpaRepository<EducationEntity, Integer> {

    Optional<EducationEntity> findByName(String name);

}
