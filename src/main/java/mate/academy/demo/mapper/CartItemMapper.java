package mate.academy.demo.mapper;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
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

    default Set<OrderItem> toOrderItem(Set<CartItem> cartItems) {
        if (cartItems == null || cartItems.isEmpty()) {
            throw new IllegalArgumentException("Cannot map empty cart items to OrderItem");
        }

        Set<OrderItem> orderItems = new HashSet<>();

        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setPrice(cartItem.getBook().getPrice());
            orderItem.setBook(cartItem.getBook());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItems.add(orderItem);
        }

        return orderItems;
    }

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
    default BigDecimal getTotal(Set<CartItem> cartItems) {
        return cartItems.stream()
                .map(cartItem -> cartItem.getBook().getPrice()
                        .multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
