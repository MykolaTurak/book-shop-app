package mate.academy.demo.service;

import mate.academy.demo.dto.order.OrderCreateRequestDto;
import mate.academy.demo.dto.order.OrderDto;
import mate.academy.demo.dto.order.OrderUpdateRequestDto;
import mate.academy.demo.dto.orderitem.OrderItemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    Page<OrderDto> findAll(Long currentUserId, Pageable pageable);

    OrderDto create(OrderCreateRequestDto createRequestDto, Long currentUserId);

    void updateStatus(OrderUpdateRequestDto updateRequestDto, Long id);

    Page<OrderItemDto> findAllById(Long orderId, Long userId, Pageable pageable);

    OrderItemDto findItem(Long orderId, Long itemId, Long currentUserId);
}
