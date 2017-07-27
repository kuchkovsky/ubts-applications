package ua.org.ubts.applicationssystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.org.ubts.applicationssystem.entity.Education;

@Repository
public interface EducationRepository extends JpaRepository<Education, Integer> {

    Education findByName(String name);

}
