package mate.academy.demo.mapper;

import mate.academy.demo.config.MapperConfig;
import mate.academy.demo.dto.shoppingcart.AddBookToShoppingCartRequestDto;
import mate.academy.demo.dto.shoppingcart.UpdateShoppingCartRequestDto;
import mate.academy.demo.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface CartItemMapper {

    @Mapping(source = "bookId", target = "book.id")
    CartItem toModel(AddBookToShoppingCartRequestDto dto);

    @Mapping(source = "quantity", target = "quantity")
    void updateFromDto(UpdateShoppingCartRequestDto dto, @MappingTarget CartItem cartItem);
}
