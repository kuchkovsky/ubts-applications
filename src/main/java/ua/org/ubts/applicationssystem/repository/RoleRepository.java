package ua.org.ubts.applicationssystem.repository;

import ua.org.ubts.applicationssystem.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {

	Role findByName(String name);

}
