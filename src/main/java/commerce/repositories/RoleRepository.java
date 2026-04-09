package commerce.repositories;

import commerce.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleRepository  extends JpaRepository<Role, Integer> {

    @Query("""
    SELECT r FROM Role r WHERE r.name = :name
""")
    Optional<Role> findRoleByName(String name);
}
