package mate.academy.demo.repository;

import mate.academy.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepisitory extends JpaRepository<User, Long> {
}
