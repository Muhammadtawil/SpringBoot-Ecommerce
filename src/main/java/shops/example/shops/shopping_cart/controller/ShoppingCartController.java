package shops.example.shops.shopping_cart.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import shops.example.shops.shopping_cart.dto.CartItemDto;
import shops.example.shops.shopping_cart.dto.ShoppingCartDto;
import shops.example.shops.shopping_cart.service.ShoppingCartService;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ShoppingCartDto addProductToCart(@PathVariable UUID userId, @RequestBody CartItemDto cartItemDto) {
        return shoppingCartService.addProductToCart(userId, cartItemDto);
    }

    @DeleteMapping("/{userId}/remove-empty")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeEmptyShoppingCart(@PathVariable UUID userId) {
        shoppingCartService.removeEmptyShoppingCart(userId);
    }
}