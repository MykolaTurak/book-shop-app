package mate.academy.demo.service;

import mate.academy.demo.dto.shoppingcart.AddBookToShoppingCartRequestDto;
import mate.academy.demo.dto.shoppingcart.ShoppingCartDto;
import mate.academy.demo.dto.shoppingcart.UpdateShoppingCartRequestDto;

public interface ShoppingCartService {
    ShoppingCartDto findShoppingCart();

    ShoppingCartDto addBook(AddBookToShoppingCartRequestDto addBookToShoppingCartRequestDto);

    ShoppingCartDto updateItemQuantity(UpdateShoppingCartRequestDto updateShoppingCartRequestDto,
                                       Long id);
}
