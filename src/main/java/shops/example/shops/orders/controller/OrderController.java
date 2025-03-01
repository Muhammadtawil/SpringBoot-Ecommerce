package shops.example.shops.orders.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import shops.example.shops.orders.dto.OrderDto;
import shops.example.shops.orders.service.OrderService;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Add a new order
    @PostMapping("/add")
    public ResponseEntity<OrderDto> addOrder(@RequestBody OrderDto orderDto) {
        OrderDto createdOrder = orderService.addOrder(orderDto);
        return ResponseEntity.ok(createdOrder);
    }

    // Update an existing order
    @PatchMapping("/update/{orderId}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable UUID orderId, @RequestBody OrderDto orderDto) {
        OrderDto updatedOrder = orderService.updateOrder(orderId, orderDto);
        return ResponseEntity.ok(updatedOrder);
    }

    // Delete an order
    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    // Get all orders for the current user
    @GetMapping("/my-orders")
    public ResponseEntity<List<OrderDto>> getAllMyOrders() {
        List<OrderDto> orders = orderService.getAllMyOrders();
        return ResponseEntity.ok(orders);
    }

    // Get all orders (Admin and Super Admin only)
    @GetMapping("/all-orders")
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<OrderDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
}
