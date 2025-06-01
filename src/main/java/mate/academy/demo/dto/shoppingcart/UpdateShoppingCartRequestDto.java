package mate.academy.demo.dto.shoppingcart;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateShoppingCartRequestDto {
    @Min(1L)
    private int quantity;
}
