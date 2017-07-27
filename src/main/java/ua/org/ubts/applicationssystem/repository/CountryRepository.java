package ua.org.ubts.applicationssystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.org.ubts.applicationssystem.entity.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

    Country findByName(String name);

}
