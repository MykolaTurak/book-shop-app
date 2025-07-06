package mate.academy.demo.service;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import mate.academy.demo.dto.order.OrderCreateRequestDto;
import mate.academy.demo.dto.order.OrderDto;
import mate.academy.demo.dto.order.OrderUpdateRequestDto;
import mate.academy.demo.dto.orderitem.OrderItemDto;
import mate.academy.demo.exeption.EntityNotFoundException;
import mate.academy.demo.exeption.OrderProcessingException;
import mate.academy.demo.mapper.CartItemMapper;
import mate.academy.demo.mapper.OrderItemMapper;
import mate.academy.demo.mapper.OrderMapper;
import mate.academy.demo.model.Order;
import mate.academy.demo.model.OrderItem;
import mate.academy.demo.model.ShoppingCart;
import mate.academy.demo.model.Status;
import mate.academy.demo.repository.CartItemRepository;
import mate.academy.demo.repository.OrderItemRepository;
import mate.academy.demo.repository.OrderRepository;
import mate.academy.demo.repository.ShoppingCartRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartService shoppingCartService;
    private final OrderMapper orderMapper;
    private final CartItemMapper cartItemMapper;
    private final OrderItemMapper orderItemMapper;

    @Override
    public Page<OrderDto> findAll(Long currentUserId, Pageable pageable) {
        return orderRepository.findAllByUserId(currentUserId, pageable).map(orderMapper::toDto);
    }

    @Override
    public OrderDto create(OrderCreateRequestDto createRequestDto, Long currentUserId) {
        Order order = orderMapper.toModel(createRequestDto);
        Order finalOrder = build(order, currentUserId);
        return orderMapper.toDto(orderRepository.save(finalOrder));
    }

    @Override
    public void updateStatus(OrderUpdateRequestDto updateRequestDto, Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "Can't find order with id: " + id
        ));
        order.setStatus(updateRequestDto.getStatus());
        orderRepository.save(order);
    }

    @Override
    public Page<OrderItemDto> findAllById(Long orderId, Long userId, Pageable pageable) {
        return orderItemRepository.findAllByOrder_IdAndOrder_User_Id(orderId, userId, pageable)
                .map(orderItemMapper::toDto);
    }

    @Override
    public OrderItemDto findItem(Long orderId, Long itemId, Long currentUserId) {
        OrderItem orderItem = orderItemRepository.findByIdAndOrder_IdAndOrder_User_Id(
                itemId, orderId, currentUserId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find item with id: " + itemId
                ));

        return orderItemMapper.toDto(orderItem);
    }

    private Order build(Order order, Long userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cant find shopping cart with user Id: " + userId));

        if (shoppingCart.getCartItems().isEmpty()) {
            throw new OrderProcessingException("Shopping cart with user id: "
                    + userId + " is empty");
        }

        order.setUser(shoppingCart.getUser());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(Status.PENDING);

        OrderItem orderItem = cartItemMapper.toOrderItem(shoppingCart.getCartItems());
        orderItem.setOrder(order);

        Set<OrderItem> orderItems = Set.of(orderItem);
        order.setOrderItems(orderItems);
        order.setTotal(orderItem.getPrice());

        shoppingCart.clearCart();

        return order;
    }

}
