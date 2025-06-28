package mate.academy.demo.repository;

import java.util.Optional;
import mate.academy.demo.model.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @EntityGraph(attributePaths = {"book"})
    Page<OrderItem> findAllByOrder_IdAndOrder_User_Id(Long orderId, Long userId, Pageable pageable);

    @EntityGraph(attributePaths = {"book"})
    Optional<OrderItem> findByIdAndOrder_IdAndOrder_User_Id(
            Long itemId, Long orderId, Long userId);
}
