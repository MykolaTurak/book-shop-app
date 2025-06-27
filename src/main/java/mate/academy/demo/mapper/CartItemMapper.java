package mate.academy.demo.mapper;

import mate.academy.demo.config.MapperConfig;
import mate.academy.demo.dto.cartitem.CartItemDto;
import mate.academy.demo.dto.shoppingcart.CartItemRequestDto;
import mate.academy.demo.dto.shoppingcart.UpdateShoppingCartRequestDto;
import mate.academy.demo.model.Book;
import mate.academy.demo.model.CartItem;
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
}

