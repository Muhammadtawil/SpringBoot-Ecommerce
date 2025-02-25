package shops.example.shops.shopping_cart.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import shops.example.shops.shopping_cart.dto.CartItemDto;
import shops.example.shops.shopping_cart.dto.ShoppingCartDto;
import shops.example.shops.shopping_cart.service.ShoppingCartService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public ShoppingCartDto getShoppingCart() {
        return shoppingCartService.getShoppingCart(); // Call the updated service method
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ShoppingCartDto> getAllShoppingCarts() {
        return shoppingCartService.getAllShoppingCarts(); // Call the service method to get all shopping carts
    }

       // Endpoint to get the user's cart items
       @GetMapping("/items")
       @ResponseStatus(HttpStatus.OK)
       public List<CartItemDto> getMyCartItems() {
           return shoppingCartService.getMyCartItems();
       }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ShoppingCartDto addProductToCart(@RequestBody CartItemDto cartItemDto) {
        return shoppingCartService.addProductToCart(cartItemDto);
    }

    // Endpoint to update the quantity of a cart item
@PatchMapping("/update/{cartItemId}")
@ResponseStatus(HttpStatus.OK)
public ShoppingCartDto updateCartItem(@PathVariable UUID cartItemId, @RequestBody CartItemDto cartItemDto) {
    return shoppingCartService.updateCartItem(cartItemId, cartItemDto);
}

    @DeleteMapping("/{userId}/remove-empty")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeEmptyShoppingCart(@PathVariable UUID userId) {
        shoppingCartService.removeEmptyShoppingCart(userId);
    }

    // Endpoint to delete a cart item
@DeleteMapping("/remove/{cartItemId}")
@ResponseStatus(HttpStatus.NO_CONTENT)
public void deleteCartItem(@PathVariable UUID cartItemId) {
    shoppingCartService.deleteCartItem(cartItemId);
}

}