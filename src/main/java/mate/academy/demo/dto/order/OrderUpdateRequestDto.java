package mate.academy.demo.dto.order;

import lombok.Getter;
import lombok.Setter;
import mate.academy.demo.model.Status;

@Getter
@Setter
public class OrderUpdateRequestDto {
    private Status status;
}
