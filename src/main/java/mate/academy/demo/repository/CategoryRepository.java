package mate.academy.demo.repository;

import java.util.Optional;
import mate.academy.demo.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
