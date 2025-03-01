package shops.example.shops.orders.service;

import java.util.List;
import java.util.stream.Collectors;

import shops.example.shops.orders.dto.OrderDto;
import shops.example.shops.orders.dto.OrderItemDto;
import shops.example.shops.orders.entity.Order;
import shops.example.shops.orders.entity.OrderItem;

public class OrderMapper {

    public static OrderDto toDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(order.getOrderId());
        orderDto.setUserId(order.getUser().getId());
        
        // Convert order items to OrderItemDto
        List<OrderItemDto> orderItemDtos = order.getOrderItems().stream()
            .map(OrderMapper::toOrderItemDto)
            .collect(Collectors.toList());
        
        // Corrected line: set the order items field instead of orderId
        orderDto.setOrderItems(orderItemDtos);  // Set the order items
        
        orderDto.setStatus(order.getStatus());
        orderDto.setTotalPrice(order.getTotalPrice());
        orderDto.setShippingAddress(order.getShippingAddress());
        orderDto.setOrderDate(order.getOrderDate());
        orderDto.setShippingDate(order.getShippingDate());
        orderDto.setDeliveryDate(order.getDeliveryDate());
        orderDto.setPaymentStatus(order.getPaymentStatus());
        orderDto.setPaymentMethod(order.getPaymentMethod());
        orderDto.setTrackingNumber(order.getTrackingNumber());
        orderDto.setShippingCarrier(order.getShippingCarrier());
        orderDto.setCustomerNotes(order.getCustomerNotes());
        orderDto.setCreatedAt(order.getCreatedAt());
        orderDto.setUpdatedAt(order.getUpdatedAt());
        
        return orderDto;
    }

    public static List<OrderDto> toDtoList(List<Order> orders) {
        return orders.stream().map(OrderMapper::toDto).collect(Collectors.toList());
    }

    // Convert OrderItem to OrderItemDto
    public static OrderItemDto toOrderItemDto(OrderItem orderItem) {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setOrderItemId(orderItem.getOrderItem_id());
        orderItemDto.setOrderId(orderItem.getOrder().getOrderId());
        orderItemDto.setProductId(orderItem.getProduct_id());
        orderItemDto.setQuantity(orderItem.getQuantity());
        orderItemDto.setPrice(orderItem.getPrice());
        
        return orderItemDto;
    }
}
