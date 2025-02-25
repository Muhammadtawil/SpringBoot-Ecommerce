package shops.example.shops.shopping_cart.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import shops.example.shops.auth.entity.User;
import shops.example.shops.auth.entity.enums.UserRole;
import shops.example.shops.auth.service.CustomUserDetails;
import shops.example.shops.shopping_cart.dto.CartItemDto;
import shops.example.shops.shopping_cart.dto.ShoppingCartDto;
import shops.example.shops.shopping_cart.entity.CartItem;
import shops.example.shops.shopping_cart.entity.ShoppingCart;
import shops.example.shops.shopping_cart.repositories.CartItemRepository;
import shops.example.shops.shopping_cart.repositories.ShoppingCartRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, CartItemRepository cartItemRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    // Helper method to get the current authenticated user
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return customUserDetails.getUser();
    }
    public ShoppingCartDto addProductToCart(CartItemDto cartItemDto) {
        User currentUser = getCurrentUser();  // Fetch the current user
        UUID userId = currentUser.getId();
    
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId);
    
        // If no shopping cart exists, create a new one
        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart();
            shoppingCart.setUserId(userId);
            shoppingCart = shoppingCartRepository.save(shoppingCart);
        }
    
        // Check if the product exists in stock, for example, by calling a product service
        boolean productInStock = checkProductStock(cartItemDto.getProductId(), cartItemDto.getQuantity());
        if (!productInStock) {
            throw new RuntimeException("Product is out of stock.");
        }
    
        // Check if the product already exists in the cart
        CartItem existingCartItem = shoppingCart.getCartItems().stream()
                .filter(item -> item.getProductId().equals(cartItemDto.getProductId()))
                .findFirst()
                .orElse(null);
    
        if (existingCartItem != null) {
            // If the product already exists, just update the quantity
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItemDto.getQuantity());
            cartItemRepository.save(existingCartItem);
        } else {
            // Otherwise, create a new CartItem
            CartItem newCartItem = new CartItem();
            newCartItem.setShoppingCart(shoppingCart);
            newCartItem.setProductId(cartItemDto.getProductId());
            newCartItem.setQuantity(cartItemDto.getQuantity());
            cartItemRepository.save(newCartItem);
            shoppingCart.getCartItems().add(newCartItem);
        }
    
        // Save the updated shopping cart with the modified CartItems
        shoppingCartRepository.save(shoppingCart);
    
        return toShoppingCartDto(shoppingCart);
    }
    
    private boolean checkProductStock(UUID productId, int quantity) {
        // Call product service or repository to check stock availability
        // For demonstration, let's assume it always returns true, but you'll integrate real stock logic here
        return true; // Placeholder, replace with actual product stock check
    }
    

    public ShoppingCartDto getShoppingCart() {
        User currentUser = getCurrentUser();  // Fetch the current user
        UUID userId = currentUser.getId();  // Get userId from the authenticated user

        // Retrieve the shopping cart for the authenticated user
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId);

        if (shoppingCart == null) {
            throw new RuntimeException("Shopping cart not found for user: " + userId);
        }

        // Convert and return as DTO
        return toShoppingCartDto(shoppingCart);
    }

    // Get the cart items for the current user's shopping cart
    public List<CartItemDto> getMyCartItems() {
        User currentUser = getCurrentUser();  // Fetch the current user
        UUID userId = currentUser.getId();

        // Fetch the shopping cart for the user
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId);

        if (shoppingCart == null) {
            throw new RuntimeException("Shopping cart not found for user: " + userId);
        }

        // Fetch all CartItems associated with the ShoppingCart
        List<CartItem> cartItems = shoppingCart.getCartItems();

        // Convert CartItems to CartItemDto
        return cartItems.stream()
                .map(this::toCartItemDto)
                .collect(Collectors.toList());
    }

    // Convert CartItem entity to CartItemDto
    private CartItemDto toCartItemDto(CartItem cartItem) {
        CartItemDto dto = new CartItemDto();
        dto.setCartItemId(cartItem.getCartItemId());
        dto.setShoppingCartId(cartItem.getShoppingCart().getCartId()); // Ensure shoppingCartId is populated
        dto.setProductId(cartItem.getProductId());
        dto.setQuantity(cartItem.getQuantity());
        return dto;
    }

    public List<ShoppingCartDto> getAllShoppingCarts() {
        User currentUser = getCurrentUser();  // Fetch the current user

        // Assuming user role is checked here, and only admins can access all carts
        if (currentUser.getUserRole() != UserRole.ADMIN && currentUser.getUserRole() != UserRole.SUPER_ADMIN) {
            throw new RuntimeException("Access denied. Only admins can view all shopping carts.");
        }

        // Fetch all active shopping carts
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findAll();

        // Convert and return all shopping carts as DTOs
        return shoppingCarts.stream()
                .map(this::toShoppingCartDto)
                .collect(Collectors.toList());
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

    public ShoppingCartDto updateCartItem(UUID cartItemId, CartItemDto cartItemDto) {
        User currentUser = getCurrentUser();  // Fetch the current user
        UUID userId = currentUser.getId();
    
        // Fetch the shopping cart for the user
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId);
    
        if (shoppingCart == null) {
            throw new RuntimeException("Shopping cart not found for user: " + userId);
        }
    
        // Fetch the cart item that needs to be updated
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
    
        if (!cartItem.getShoppingCart().getUserId().equals(userId)) {
            throw new RuntimeException("This cart item does not belong to the current user");
        }
    
        // Update the quantity of the cart item
        cartItem.setQuantity(cartItemDto.getQuantity());
        cartItemRepository.save(cartItem);
    
        // Return the updated shopping cart
        return toShoppingCartDto(shoppingCart);
    }
    
    public void deleteCartItem(UUID cartItemId) {
        User currentUser = getCurrentUser();  // Fetch the current user
        UUID userId = currentUser.getId();

        // Fetch the shopping cart for the user
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId);

        if (shoppingCart == null) {
            throw new RuntimeException("Shopping cart not found for user: " + userId);
        }

        // Fetch the cart item to be deleted
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (!cartItem.getShoppingCart().getUserId().equals(userId)) {
            throw new RuntimeException("This cart item does not belong to the current user");
        }

        // Remove the cart item from the cart
        cartItemRepository.delete(cartItem);

        // You could also remove it from the shoppingCart.getCartItems() if necessary
        shoppingCart.getCartItems().remove(cartItem);
        shoppingCartRepository.save(shoppingCart);
    }
}
