package shops.example.shops.shopping_cart.entity;

import java.util.UUID;
import jakarta.persistence.*;
import lombok.Data;

@Table(name = "cart_items", indexes = {
    @Index(name = "idx_cart_item_product_id", columnList = "product_id"),
    @Index(name = "idx_cart_item_shopping_cart_id", columnList = "shopping_cart_id")
})
@Entity
@Data
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID cartItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopping_cart_id", nullable = false)
    private ShoppingCart shoppingCart;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "quantity", nullable = false)
    private int quantity;
}



