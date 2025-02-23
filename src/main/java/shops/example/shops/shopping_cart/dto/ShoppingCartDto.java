package shops.example.shops.shopping_cart.dto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ShoppingCartDto {
    private UUID cartId;
    private UUID userId;
    private List<UUID> cartItemIds;
    private Date createdAt;
    private Date updatedAt;

    // Getters and Setters
    public UUID getCartId() {
        return cartId;
    }

    public void setCartId(UUID cartId) {
        this.cartId = cartId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public List<UUID> getCartItemIds() {
        return cartItemIds;
    }

    public void setCartItemIds(List<UUID> cartItemIds) {
        this.cartItemIds = cartItemIds;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
