package mate.academy.demo.mapper;

import java.math.BigDecimal;
import mate.academy.demo.config.MapperConfig;
import mate.academy.demo.dto.cartitem.CartItemDto;
import mate.academy.demo.dto.shoppingcart.CartItemRequestDto;
import mate.academy.demo.dto.shoppingcart.UpdateShoppingCartRequestDto;
import mate.academy.demo.model.Book;
import mate.academy.demo.model.CartItem;
import mate.academy.demo.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface CartItemMapper {

    @Mapping(target = "book", source = "bookId", qualifiedByName = "mapBookIdToBook")
    CartItem toModel(CartItemRequestDto dto);

    @Mapping(target = "bookId", source = "book.id")
    @Mapping(target = "bookTitle", source = "book.title")
    CartItemDto toDto(CartItem cartItem);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "price", source = "cartItem",
            qualifiedByName = "calculateOrderItemPrice")
    OrderItem toOrderItem(CartItem cartItem);

    void updateFromDto(UpdateShoppingCartRequestDto dto, @MappingTarget CartItem cartItem);

    @Named("mapBookIdToBook")
    default Book mapBookIdToBook(Long bookId) {
        if (bookId == null) {
            return null;
        }
        Book book = new Book();
        book.setId(bookId);
        return book;
    }

    @Named("calculateOrderItemPrice")
    default BigDecimal calculatePrice(CartItem cartItem) {
        if (cartItem != null && cartItem.getBook() != null
                && cartItem.getBook().getPrice() != null) {
            return cartItem.getBook().getPrice().multiply(new BigDecimal(cartItem.getQuantity()));
        }
        return BigDecimal.ZERO;
    }
}
