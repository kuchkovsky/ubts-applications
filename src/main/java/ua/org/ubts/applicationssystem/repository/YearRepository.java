package ua.org.ubts.applicationssystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.org.ubts.applicationssystem.entity.Year;

@Repository
public interface YearRepository extends JpaRepository<Year, Integer> {

    Year findByValue(Integer value);

}
