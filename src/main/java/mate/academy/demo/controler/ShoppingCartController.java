package mate.academy.demo.controler;

import lombok.RequiredArgsConstructor;
import mate.academy.demo.dto.shoppingcart.AddBookToShoppingCartRequestDto;
import mate.academy.demo.dto.shoppingcart.ShoppingCartDto;
import mate.academy.demo.dto.shoppingcart.UpdateShoppingCartRequestDto;
import mate.academy.demo.service.ShoppingCartService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping
    public ShoppingCartDto findShoppingCart() {
        return shoppingCartService.findShoppingCart();
    }

    @PostMapping
    public ShoppingCartDto addBook(
            @RequestBody AddBookToShoppingCartRequestDto addBookToShoppingCartRequestDto) {
        return shoppingCartService.addBook(addBookToShoppingCartRequestDto);
    }

    @PutMapping(path = "/items/{id}")
    public ShoppingCartDto updateItemQuantity(
            @RequestBody UpdateShoppingCartRequestDto updateShoppingCartRequestDto,
            @PathVariable Long id) {
        return shoppingCartService.updateItemQuantity(updateShoppingCartRequestDto, id);
    }
}
