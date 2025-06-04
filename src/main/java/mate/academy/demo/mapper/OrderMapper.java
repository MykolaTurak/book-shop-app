package mate.academy.demo.mapper;

import mate.academy.demo.config.MapperConfig;
import mate.academy.demo.dto.order.OrderCreateRequestDto;
import mate.academy.demo.dto.order.OrderDto;
import mate.academy.demo.model.Order;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class, uses = OrderItemMapper.class)
public interface OrderMapper {
    OrderDto toDto(Order order);

    Order toModel(OrderCreateRequestDto orderCreateRequestDto);
}
