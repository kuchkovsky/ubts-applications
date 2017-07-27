package ua.org.ubts.applicationssystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.org.ubts.applicationssystem.entity.Residence;

@Repository
public interface ResidenceRepository extends JpaRepository<Residence, Long> {

}
