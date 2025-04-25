package mate.academy.demo.repository;

import java.util.Optional;
import mate.academy.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepisitory extends JpaRepository<User, Long> {
    Optional<User> getUserByEmail(String email);

    boolean existsByEmail(String email);
}
