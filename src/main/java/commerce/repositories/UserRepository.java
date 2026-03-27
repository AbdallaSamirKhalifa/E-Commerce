package commerce.repositories;

import commerce.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("""
    SELECT CASE
               WHEN (COUNT(1) > 0)
                    THEN 'true'
               ELSE 'false'
               END
    FROM User u WHERE u.username = :username
""")
    boolean isUsernameExists(String username);

    @Query("""
    SELECT CASE
               WHEN (COUNT(1) > 0)
                    THEN 'true'
               ELSE 'false'
               END
    FROM User u WHERE u.email = :email
""")
    boolean isEmailExists(String email);
    @Query("""
    SELECT u FROM User u WHERE u.username = :username
""")
    Optional<User> findUserByUsername(String username);
}
