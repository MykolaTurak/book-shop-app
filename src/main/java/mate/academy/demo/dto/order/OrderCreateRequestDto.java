package mate.academy.demo.dto.order;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderCreateRequestDto {
    @NotBlank
    private String shippingAddress;
}
