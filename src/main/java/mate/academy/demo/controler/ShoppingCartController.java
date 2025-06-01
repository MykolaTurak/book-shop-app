package mate.academy.demo.controler;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import mate.academy.demo.dto.shoppingcart.CartItemRequestDto;
import mate.academy.demo.dto.shoppingcart.ShoppingCartDto;
import mate.academy.demo.dto.shoppingcart.UpdateShoppingCartRequestDto;
import mate.academy.demo.service.AuthenticationService;
import mate.academy.demo.service.ShoppingCartService;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    private final AuthenticationService authenticationService;

    @Operation(summary = "Find shopping cart", description = "Find shopping card by authentication")
    @GetMapping
    public ShoppingCartDto findShoppingCart() {

        return shoppingCartService.findShoppingCartById(authenticationService.getCurrentUserId());
    }

    @Operation(summary = "Add book to shopping cart",
            description = "Add book to shopping cart by it's id")
    @PostMapping
    public ShoppingCartDto addBook(
            @RequestBody CartItemRequestDto cartItemRequestDto) {
        return shoppingCartService.addBook(cartItemRequestDto,
                authenticationService.getCurrentUserId());
    }

    @Operation(summary = "Update quantity of item", description = "Update item quantity by it's id")
    @PutMapping(path = "/items/{id}")
    public ShoppingCartDto updateItemQuantity(
            @RequestBody UpdateShoppingCartRequestDto updateShoppingCartRequestDto,
            @PathVariable Long id) {
        return shoppingCartService.updateItemQuantity(updateShoppingCartRequestDto,
                id, authenticationService.getCurrentUserId());
    }

    @Operation(summary = "Delete item", description = "Delete item by it's id")
    @DeleteMapping(path = "/items/{id}")
    public ShoppingCartDto deleteItem(@PathVariable Long id) {
        return shoppingCartService.deleteItemById(id, authenticationService.getCurrentUserId());
    }
}
