// filepath: /d:/Java/shops/src/main/java/shops/example/shops/orders/entity/Order.java
package shops.example.shops.orders.entity;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.Data;
import shops.example.shops.orders.enums.OrderStatus;
import shops.example.shops.payments.enums.PaymentStatus;
import shops.example.shops.auth.entity.User;

@Entity
@Table(name = "orders", indexes = {
    @Index(name = "idx_order_user_id", columnList = "user_id"),
    @Index(name = "idx_order_status", columnList = "status")
})
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID order_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;
    

    @Column(name ="status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "total_price", nullable = false)
    private double totalPrice;

    @Column(name = "shipping_address", nullable = false)
    private String shippingAddress;

    @Column(name = "order_date", nullable = false)
    private Date orderDate;

    @Column(name = "shipping_date", nullable = true)
    private Date shippingDate;

    @Column(name = "delivery_date", nullable = true)
    private Date deliveryDate;

    @Column(name = "payment_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    @Column(name = "tracking_number", nullable = true)
    private String trackingNumber;

    @Column(name = "shipping_carrier", nullable = true)
    private String shippingCarrier;

    @Column(name = "customer_notes", nullable = true)
    private String customerNotes;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
}