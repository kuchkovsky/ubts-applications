package ua.org.ubts.applicationssystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.org.ubts.applicationssystem.entity.MaritalData;

@Repository
public interface MaritalDataRepository extends JpaRepository<MaritalData, Integer> {

}
