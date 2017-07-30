package ua.org.ubts.applicationssystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.org.ubts.applicationssystem.entity.ChurchData;

@Repository
public interface ChurchDataRepository extends JpaRepository<ChurchData, Integer> {
}
