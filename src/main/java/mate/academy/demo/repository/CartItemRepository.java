package mate.academy.demo.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import mate.academy.demo.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query("SELECT ci FROM CartItem ci JOIN FETCH ci.book WHERE ci.shoppingCart.user.id = :userId")
    List<CartItem> findAllByShoppingCartUserIdWithBook(@Param("userId") Long userId);

    Optional<CartItem> findByIdAndShoppingCartId(Long id, Long shoppingCartId);

    @Query("SELECT ci FROM CartItem ci WHERE ci.shoppingCart.id ="
            + " :shoppingCartId AND ci.id <> :excludedId")
    Set<CartItem> findAllByShoppingCartIdAndIdNot(@Param("shoppingCartId") Long shoppingCartId,
                                                  @Param("excludedId") Long excludedId);
}
