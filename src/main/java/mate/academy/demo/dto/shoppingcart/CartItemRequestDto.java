package mate.academy.demo.dto.shoppingcart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemRequestDto {
    @NotNull
    private Long bookId;
    @Min(1L)
    private int quantity;
}
