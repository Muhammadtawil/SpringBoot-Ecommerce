package shops.example.shops.shopping_cart.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import shops.example.shops.shopping_cart.entity.ShoppingCart;
import java.util.UUID;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, UUID> {
    ShoppingCart findByUserId(UUID userId);
}