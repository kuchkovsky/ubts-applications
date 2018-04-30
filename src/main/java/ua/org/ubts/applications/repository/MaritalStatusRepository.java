package ua.org.ubts.applications.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.org.ubts.applications.entity.MaritalStatusEntity;

import java.util.Optional;

@Repository
public interface MaritalStatusRepository extends JpaRepository<MaritalStatusEntity, Integer> {

    Optional<MaritalStatusEntity> findByName(String name);

}
