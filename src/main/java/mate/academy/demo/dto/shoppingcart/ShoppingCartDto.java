package mate.academy.demo.dto.shoppingcart;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import mate.academy.demo.dto.cartitem.CartItemDto;

@Getter
@Setter
public class ShoppingCartDto {
    private Long id;
    private Long userId;
    private List<CartItemDto> cartItems;
}
