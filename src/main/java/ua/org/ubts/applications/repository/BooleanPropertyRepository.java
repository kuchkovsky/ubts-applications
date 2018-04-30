package ua.org.ubts.applications.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.org.ubts.applications.entity.BooleanPropertyEntity;

import java.util.Optional;

@Repository
public interface BooleanPropertyRepository extends JpaRepository<BooleanPropertyEntity, Integer> {

    Optional<BooleanPropertyEntity> findByKey(String key);

}
