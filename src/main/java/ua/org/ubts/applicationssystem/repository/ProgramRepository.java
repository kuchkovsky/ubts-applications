package ua.org.ubts.applicationssystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.org.ubts.applicationssystem.entity.Program;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Integer> {

    Program findByNameAndInfo(String name, String info);

}
