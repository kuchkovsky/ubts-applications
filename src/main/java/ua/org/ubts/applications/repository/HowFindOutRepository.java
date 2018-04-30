package ua.org.ubts.applications.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.org.ubts.applications.entity.HowFindOutEntity;

import java.util.Optional;

@Repository
public interface HowFindOutRepository extends JpaRepository<HowFindOutEntity, Integer> {

    Optional<HowFindOutEntity> findByName(String name);

}
