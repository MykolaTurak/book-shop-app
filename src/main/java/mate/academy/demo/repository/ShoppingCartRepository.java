package mate.academy.demo.repository;

import java.util.Optional;
import mate.academy.demo.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    @Query("SELECT sc FROM ShoppingCart sc WHERE sc.user.id = :id")
    Optional<ShoppingCart> findByUserId(Long id);
}
