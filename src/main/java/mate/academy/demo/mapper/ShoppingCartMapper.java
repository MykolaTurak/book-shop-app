package mate.academy.demo.mapper;

import mate.academy.demo.config.MapperConfig;
import mate.academy.demo.dto.shoppingcart.ShoppingCartDto;
import mate.academy.demo.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = CartItemMapper.class)
public interface ShoppingCartMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "cartItems", target = "cartItems")
    ShoppingCartDto toDto(ShoppingCart shoppingCart);
}
