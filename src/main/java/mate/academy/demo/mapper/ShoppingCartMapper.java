package mate.academy.demo.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import mate.academy.demo.config.MapperConfig;
import mate.academy.demo.dto.cartitem.CartItemDto;
import mate.academy.demo.dto.shoppingcart.ShoppingCartDto;
import mate.academy.demo.model.CartItem;
import mate.academy.demo.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface ShoppingCartMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(target = "cartItems",
            expression = "java(mapCartItemsToDto(shoppingCart.getCartItems()))")
    ShoppingCartDto toDto(ShoppingCart shoppingCart);

    default List<CartItemDto> mapCartItemsToDto(Set<CartItem> cartItems) {
        return cartItems.stream()
                .map(item -> {
                    CartItemDto dto = new CartItemDto();
                    dto.setId(item.getId());
                    dto.setBookId(item.getBook().getId());
                    dto.setBookTitle(item.getBook().getTitle());
                    dto.setQuantity(item.getQuantity());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}

