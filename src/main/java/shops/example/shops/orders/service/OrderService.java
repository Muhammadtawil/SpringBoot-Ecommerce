package shops.example.shops.orders.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shops.example.shops.auth.entity.User;
import shops.example.shops.auth.entity.enums.UserRole;
import shops.example.shops.auth.service.AuthenticationService;
import shops.example.shops.orders.Repositories.OrderItemRepository;
import shops.example.shops.orders.Repositories.OrderRepository;
import shops.example.shops.orders.dto.OrderDto;
import shops.example.shops.orders.dto.OrderItemDto;
import shops.example.shops.orders.entity.Order;
import shops.example.shops.orders.entity.OrderItem;

import shops.example.shops.orders.enums.OrderStatus;
import shops.example.shops.payments.enums.PaymentStatus;
  // You might need to implement this to map Order to OrderDto.

  @Service
  public class OrderService {
  
      @Autowired
      private OrderRepository orderRepository;
  
      @Autowired
      private OrderItemRepository orderItemRepository;
  
      @Autowired
      private AuthenticationService authenticationService;
  
      // Add Order
      public OrderDto addOrder(OrderDto orderDto) {
          User currentUser = authenticationService.getCurrentUser();
          
          Order order = new Order();
          order.setUser(currentUser);
          order.setStatus(OrderStatus.PENDING);
          order.setTotalPrice(orderDto.getTotalPrice());
          order.setShippingAddress(orderDto.getShippingAddress());
          order.setOrderDate(new java.sql.Date(System.currentTimeMillis()));
          order.setPaymentStatus(PaymentStatus.PENDING);
          order.setPaymentMethod(orderDto.getPaymentMethod());
          order.setTrackingNumber(orderDto.getTrackingNumber());
          order.setShippingCarrier(orderDto.getShippingCarrier());
          order.setCustomerNotes(orderDto.getCustomerNotes());
          
          order = orderRepository.save(order);
  
          // Save OrderItems
          for (OrderItemDto orderItemDto : orderDto.getOrderItems()) {  // Corrected here
              OrderItem orderItem = new OrderItem();
              orderItem.setOrder(order);
              orderItem.setProduct_id(orderItemDto.getProductId());  // Corrected here
              // You should fetch product information and set the price and quantity accordingly.
              orderItemRepository.save(orderItem);
          }
  
          return OrderMapper.toDto(order);
      }
  
      // Update Order
      public OrderDto updateOrder(UUID orderId, OrderDto orderDto) {
          Order order = orderRepository.findById(orderId)
              .orElseThrow(() -> new RuntimeException("Order not found"));
  
          User currentUser = authenticationService.getCurrentUser();
          if (!order.getUser().equals(currentUser)) {
              throw new RuntimeException("Unauthorized");
          }
  
          order.setStatus(orderDto.getStatus());
          order.setShippingAddress(orderDto.getShippingAddress());
          order.setPaymentStatus(orderDto.getPaymentStatus());
          order.setTrackingNumber(orderDto.getTrackingNumber());
          order.setShippingCarrier(orderDto.getShippingCarrier());
          order.setCustomerNotes(orderDto.getCustomerNotes());
  
          order = orderRepository.save(order);
          
          return OrderMapper.toDto(order);
      }
  
      // Delete Order
      public void deleteOrder(UUID orderId) {
          Order order = orderRepository.findById(orderId)
              .orElseThrow(() -> new RuntimeException("Order not found"));
  
          User currentUser = authenticationService.getCurrentUser();
          if (!order.getUser().equals(currentUser)) {
              throw new RuntimeException("Unauthorized");
          }
  
          orderRepository.delete(order);
      }
  
      // Get all orders for the current user
      public List<OrderDto> getAllMyOrders() {
          User currentUser = authenticationService.getCurrentUser();
          
          // Use the correct method to fetch orders by user ID
          List<Order> orders = orderRepository.findByUserId(currentUser.getId());
          
          return OrderMapper.toDtoList(orders);
      }
  
      // Get all orders (Admin & Super Admin)
      public List<OrderDto> getAllOrders() {
          User currentUser = authenticationService.getCurrentUser();
          if (currentUser.getUserRole() != UserRole.ADMIN && currentUser.getUserRole() != UserRole.SUPER_ADMIN) {
              throw new RuntimeException("Unauthorized");
          }
  
          List<Order> orders = orderRepository.findAll();
          return OrderMapper.toDtoList(orders);
      }
  }