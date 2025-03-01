package shops.example.shops.orders.Repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import shops.example.shops.orders.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
    List<OrderItem> findByOrderOrderId(UUID orderId); // For getting order items by order ID
}
