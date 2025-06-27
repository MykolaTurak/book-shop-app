package mate.academy.demo.controler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import mate.academy.demo.dto.order.OrderCreateRequestDto;
import mate.academy.demo.dto.order.OrderDto;
import mate.academy.demo.dto.order.OrderUpdateRequestDto;
import mate.academy.demo.dto.orderitem.OrderItemDto;
import mate.academy.demo.service.AuthenticationService;
import mate.academy.demo.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name = "Order management", description = "Endpoints for managing orders")
public class OrderController {
    private final OrderService orderService;
    private final AuthenticationService authenticationService;

    @Operation(summary = "Get all orders", description = "Getting all orders by user")
    @GetMapping
    public Page<OrderDto> findAll(Pageable pageable) {
        return orderService.findAll(authenticationService.getCurrentUserId(), pageable);
    }

    @Operation(summary = "Get all items", description = "Finding all items of certain order")
    @GetMapping("/{id}/items")
    public Page<OrderItemDto> findAll(@PathVariable Long id, Pageable pageable) {
        return orderService.findAllById(id, authenticationService.getCurrentUserId(), pageable);
    }

    @Operation(summary = "Get item", description = "Finding single items of certain order")
    @GetMapping("/{orderId}/items/{itemId}")
    public OrderItemDto findItem(@PathVariable Long orderId, @PathVariable Long itemId) {
        return orderService.findItem(orderId, itemId, authenticationService.getCurrentUserId());
    }

    @Operation(summary = "Place order", description = "Placing order of current user")
    @PostMapping
    public OrderDto place(@Valid @RequestBody OrderCreateRequestDto createRequestDto) {
        return orderService.create(createRequestDto, authenticationService.getCurrentUserId());
    }

    @Operation(summary = "Update order status", description = "Updating status of order")
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public void updateStatus(@PathVariable Long id,
                             @NotNull @RequestBody OrderUpdateRequestDto updateRequestDto) {
        orderService.updateStatus(updateRequestDto, id);
    }
}
