package ua.org.ubts.applications.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.org.ubts.applications.entity.YearEntity;

import java.util.Optional;

@Repository
public interface YearRepository extends JpaRepository<YearEntity, Integer> {

    Optional<YearEntity> findByValue(Integer value);

}
