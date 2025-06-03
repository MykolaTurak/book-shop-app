package mate.academy.demo.service;

import mate.academy.demo.dto.shoppingcart.CartItemRequestDto;
import mate.academy.demo.dto.shoppingcart.ShoppingCartDto;
import mate.academy.demo.dto.shoppingcart.UpdateShoppingCartRequestDto;
import mate.academy.demo.model.User;

public interface ShoppingCartService {
    ShoppingCartDto findShoppingCartById(Long userId);

    ShoppingCartDto addBook(CartItemRequestDto cartItemRequestDto, Long userId);

    ShoppingCartDto updateItemQuantity(UpdateShoppingCartRequestDto updateShoppingCartRequestDto,
                                       Long id, Long userId);

    void deleteItemById(Long id, Long userId);

    void createEmptyCart(User user);
}
