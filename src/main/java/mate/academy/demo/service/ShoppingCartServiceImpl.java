package mate.academy.demo.service;

import lombok.RequiredArgsConstructor;
import mate.academy.demo.dto.shoppingcart.AddBookToShoppingCartRequestDto;
import mate.academy.demo.dto.shoppingcart.ShoppingCartDto;
import mate.academy.demo.dto.shoppingcart.UpdateShoppingCartRequestDto;
import mate.academy.demo.exeption.EntityNotFoundException;
import mate.academy.demo.mapper.CartItemMapper;
import mate.academy.demo.mapper.ShoppingCartMapper;
import mate.academy.demo.model.CartItem;
import mate.academy.demo.model.ShoppingCart;
import mate.academy.demo.model.User;
import mate.academy.demo.repository.BookRepository;
import mate.academy.demo.repository.CartItemRepository;
import mate.academy.demo.repository.ShoppingCartRepository;
import mate.academy.demo.repository.UserRepisitory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepisitory userRepisitory;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;

    @Override
    public ShoppingCartDto findShoppingCart() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepisitory.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cant find user with email: " + authentication.getName()));

        return shoppingCartRepository.findById(user.getId())
                .map(shoppingCartMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cant find shopping cart with id: " + user.getId()));
    }

    @Override
    public ShoppingCartDto addBook(
            AddBookToShoppingCartRequestDto addBookToShoppingCartRequestDto) {

        if (!bookRepository.existsById(addBookToShoppingCartRequestDto.getBookId())) {
            throw new EntityNotFoundException(
                    "Book with id: " + addBookToShoppingCartRequestDto.getBookId()
                            + " - doesn't exist");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepisitory.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cant find user with email: " + authentication.getName()));

        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cant find shopping cart with user: " + user));

        CartItem cartItem = cartItemMapper.toModel(addBookToShoppingCartRequestDto);
        cartItem.setShoppingCart(shoppingCart);
        CartItem savedCartItem = cartItemRepository.save(cartItem);
        shoppingCart.getCartItems().add(savedCartItem);

        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public ShoppingCartDto updateItemQuantity(
            UpdateShoppingCartRequestDto updateShoppingCartRequestDto, Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepisitory.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(
                    "Cant find user with email: " + authentication.getName()));

        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                    "Cant find shopping cart with user: " + user));
        CartItem cartItem = shoppingCart.getCartItems().stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("CartItem with id " + id
                        + " not found"));

        cartItem.setQuantity(updateShoppingCartRequestDto.getQuantity());

        return shoppingCartMapper.toDto(shoppingCartRepository.save(shoppingCart));
    }
}
