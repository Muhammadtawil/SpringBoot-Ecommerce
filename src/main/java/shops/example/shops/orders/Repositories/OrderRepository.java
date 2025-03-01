package shops.example.shops.orders.Repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import shops.example.shops.orders.entity.Order;
import shops.example.shops.orders.enums.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    @Query("SELECT o FROM Order o WHERE o.status = :status")
List<Order> findByStatus(@Param("status") OrderStatus status);
List<Order> findByUserId(UUID userId); 

}

