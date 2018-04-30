package ua.org.ubts.applications.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.org.ubts.applications.entity.ProgramEntity;

import java.util.Optional;

@Repository
public interface ProgramRepository extends JpaRepository<ProgramEntity, Integer> {

    Optional<ProgramEntity> findByNameAndInfo(String name, String info);

}
