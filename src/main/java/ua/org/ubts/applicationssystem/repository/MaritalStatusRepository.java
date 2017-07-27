package ua.org.ubts.applicationssystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.org.ubts.applicationssystem.entity.MaritalStatus;

@Repository
public interface MaritalStatusRepository extends JpaRepository<MaritalStatus, Integer> {

    MaritalStatus findByName(String name);

}
