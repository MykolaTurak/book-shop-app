package mate.academy.demo.repository;

import mate.academy.demo.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = {"orderItems.book", "orderItems"})
    Page<Order> findAllByUserId(Long userId, Pageable pageable);
}
