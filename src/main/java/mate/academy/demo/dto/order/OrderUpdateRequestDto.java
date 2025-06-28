package mate.academy.demo.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import mate.academy.demo.model.Status;

@Getter
@Setter
public class OrderUpdateRequestDto {
    @NotNull
    private Status status;
}
