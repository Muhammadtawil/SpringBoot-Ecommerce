package shops.example.shops.shopping_cart.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import shops.example.shops.auth.entity.User;
import shops.example.shops.auth.service.CustomUserDetails;
import shops.example.shops.shopping_cart.dto.CartItemDto;
import shops.example.shops.shopping_cart.dto.ShoppingCartDto;
import shops.example.shops.shopping_cart.entity.CartItem;
import shops.example.shops.shopping_cart.entity.ShoppingCart;
import shops.example.shops.shopping_cart.repositories.CartItemRepository;
import shops.example.shops.shopping_cart.repositories.ShoppingCartRepository;

import java.util.UUID;

@Service
public class ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, CartItemRepository cartItemRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public ShoppingCartDto addProductToCart(UUID userId, CartItemDto cartItemDto) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User currentUser = customUserDetails.getUser();
        // If no shopping cart exists, create a new one
        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart();
            shoppingCart.setUserId(userId);
            shoppingCart = shoppingCartRepository.save(shoppingCart);
        }

        // Create CartItem for the product and add it to the cart
        CartItem cartItem = new CartItem();
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setProductId(cartItemDto.getProductId());
        cartItem.setQuantity(cartItemDto.getQuantity());
        cartItem = cartItemRepository.save(cartItem);

        // Add the CartItem to the ShoppingCart
        shoppingCart.setUserId(currentUser.getId());
        shoppingCart.getCartItems().add(cartItem);

        // Save the shopping cart with updated CartItems
        shoppingCartRepository.save(shoppingCart);

        // Return updated ShoppingCart as DTO
        return toShoppingCartDto(shoppingCart);
    }

    public ShoppingCartDto getShoppingCart(UUID userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId);
        if (shoppingCart == null) {
            throw new RuntimeException("Shopping cart not found");
        }
        return toShoppingCartDto(shoppingCart);
    }

    public ShoppingCartDto getShoppingCartById(UUID cartId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Shopping cart not found"));
        return toShoppingCartDto(shoppingCart);
    }

    // Logic to delete shopping cart if it's empty
    public void removeEmptyShoppingCart(UUID userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId);

        if (shoppingCart != null && shoppingCart.getCartItems().isEmpty()) {
            shoppingCartRepository.delete(shoppingCart);
        }
    }

    private ShoppingCartDto toShoppingCartDto(ShoppingCart shoppingCart) {
        ShoppingCartDto dto = new ShoppingCartDto();
        dto.setCartId(shoppingCart.getCartId());
        dto.setUserId(shoppingCart.getUserId());
        dto.setCartItemIds(shoppingCart.getCartItems().stream()
                .map(CartItem::getCartItemId)
                .toList());
        dto.setCreatedAt(shoppingCart.getCreatedAt());
        dto.setUpdatedAt(shoppingCart.getUpdatedAt());
        return dto;
    }
}