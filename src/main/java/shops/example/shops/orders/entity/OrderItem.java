package shops.example.shops.orders.entity;

import java.util.UUID;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "order_items", indexes = {
    @Index(name = "idx_order_item_order_id", columnList = "order_id"),
    @Index(name = "idx_order_item_product_id", columnList = "product_id")
})
@Data
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID orderItem_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "product_id", nullable = false)
    private UUID product_id;

    @Column(name="quantity", nullable = false)
    private int quantity;

    @Column(name="price", nullable = false)
    private double price;
}


