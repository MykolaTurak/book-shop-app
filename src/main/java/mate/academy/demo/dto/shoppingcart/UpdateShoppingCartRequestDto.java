package mate.academy.demo.dto.shoppingcart;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateShoppingCartRequestDto {
    @Positive
    private int quantity;
}
