package ua.org.ubts.applications.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.org.ubts.applications.entity.StudentEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

    @Query("SELECT s FROM StudentEntity s WHERE s.firstName = ?1 AND s.middleName = ?2 AND s.lastName = ?3")
    Optional<StudentEntity> findByFullName(String firstName, String middleName, String lastName);

    @Query("SELECT s FROM StudentEntity s WHERE s.entryYear.value IN :years")
    List<StudentEntity> findByEntryYears(@Param("years") List<Integer> entryYears);

}
