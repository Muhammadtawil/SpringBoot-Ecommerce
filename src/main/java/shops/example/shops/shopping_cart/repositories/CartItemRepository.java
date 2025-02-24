package shops.example.shops.shopping_cart.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import shops.example.shops.shopping_cart.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    // Custom query if needed, for now, JpaRepository should be enough
}
