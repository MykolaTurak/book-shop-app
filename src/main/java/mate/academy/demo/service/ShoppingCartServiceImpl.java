package mate.academy.demo.service;

import jakarta.transaction.Transactional;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.demo.dto.shoppingcart.CartItemRequestDto;
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
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepisitory userRepisitory;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;

    @Override
    public ShoppingCartDto findShoppingCartById(Long userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cant find shopping cart with id: " + userId));

        shoppingCart.getCartItems().forEach(cartItem -> {
            System.out.println("CartItem id: " + cartItem.getId());
            if (cartItem.getBook() != null) {
                System.out.println("Book id: " + cartItem.getBook().getId());
                System.out.println("Book title: " + cartItem.getBook().getTitle());
            } else {
                System.out.println("Book is null");
            }
        });
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public ShoppingCartDto addBook(
            CartItemRequestDto cartItemRequestDto, Long userId) {

        if (!bookRepository.existsById(cartItemRequestDto.getBookId())) {
            throw new EntityNotFoundException(
                    "Book with id: " + cartItemRequestDto.getBookId()
                            + " - doesn't exist");
        }

        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cant find shopping cart with user id: " + userId));

        CartItem cartItem = cartItemMapper.toModel(cartItemRequestDto);
        cartItem.setShoppingCart(shoppingCart);
        CartItem savedCartItem = cartItemRepository.save(cartItem);
        shoppingCart.getCartItems().add(savedCartItem);

        return shoppingCartMapper.toDto(shoppingCartRepository.save(shoppingCart));
    }

    @Override
    public ShoppingCartDto updateItemQuantity(
            UpdateShoppingCartRequestDto updateShoppingCartRequestDto, Long id, Long userId) {

        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                    "Cant find shopping cart with user id: " + userId));
        CartItem cartItem = shoppingCart.getCartItems().stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("CartItem with id " + id
                        + " not found"));

        cartItem.setQuantity(updateShoppingCartRequestDto.getQuantity());

        return shoppingCartMapper.toDto(shoppingCartRepository.save(shoppingCart));
    }

    @Override
    public ShoppingCartDto deleteItemById(Long id, Long userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cant find shopping cart with user Id: " + userId));
        Set<CartItem> items = shoppingCart.getCartItems().stream()
                .filter(item -> !Objects.equals(item.getId(), id))
                .collect(Collectors.toSet());

        shoppingCart.getCartItems().removeIf(item -> Objects.equals(item.getId(), id));

        return shoppingCartMapper.toDto(shoppingCartRepository.save(shoppingCart));
    }

    @Override
    public void createEmptyCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartRepository.save(shoppingCart);
    }
}
