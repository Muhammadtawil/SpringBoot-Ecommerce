package shops.example.shops;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import shops.example.shops.auth.service.CustomUserDetails;
import shops.example.shops.auth.service.CustomUserDetailsService;
import shops.example.shops.auth.service.JwtService;
import shops.example.shops.products.controller.ProductController;
import shops.example.shops.products.dto.ProductDto;
import shops.example.shops.products.entity.Product;
import shops.example.shops.products.service.ProductService;
import shops.example.shops.auth.entity.User;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username="moetawil", roles={"SUPER_ADMIN"})
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;  // MockMvc is used to simulate HTTP requests

    @SuppressWarnings("unused")
    @Autowired
    private ProductController productController;  // Autowire the ProductController

    @SuppressWarnings("removal")
    @MockBean
    private CustomUserDetailsService customUserDetailsService;  // Mock the CustomUserDetailsService

    @SuppressWarnings("removal")
    @MockBean
    private JwtService jwtService; // Mock the JwtService

    @SuppressWarnings("removal")
    @MockBean
    private ProductService productService; // Mock the ProductService

    private String jwtToken;

    private User mockUser;

    @BeforeEach
    public void setup() {
        // Mock the JWT token for the test
        jwtToken = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiU1VQRVJfQURNSU4iLCJzdWIiOiJtdWhhbW1hZC50YXdpbEBob3RtYWlsLmNvbSIsImlhdCI6MTc0MTkxMjM5NiwiZXhwIjoxNzQxOTk4Nzk2fQ.Z3-roqm0n2TSS2t0aE9666ctXGXtSiFOmuQB9TZTya4";  // Example token

        // Mock the User object
        // mockUser = new User();
        mockUser.setEmail("username");
        mockUser.setPassword("password");

        // Mock the JwtService to return valid results for JWT processing
        when(jwtService.extractEmail(jwtToken)).thenReturn("username");
        when(jwtService.isTokenValid(jwtToken, new CustomUserDetails(mockUser))).thenReturn(true);

        // Mock the CustomUserDetailsService to return a valid user
        when(customUserDetailsService.loadUserByUsername("username"))
            .thenReturn(new CustomUserDetails(mockUser));
    }

    @Test
    public void testCreateProduct() throws Exception {
        // Arrange: Create a DTO for the new product
        ProductDto productDto = new ProductDto();
        productDto.setId(UUID.randomUUID());
        productDto.setName("New Product");

        // Mock the service to return the created product
        when(productService.createProduct(productDto)).thenReturn(productDto);

        // Act and Assert: Perform the POST request and check the response
        mockMvc.perform(post("/api/v1/products")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken) // Add JWT token here
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"New Product\", \"price\": 50.0 }"))
                .andExpect(status().isOk())  // Status should be 200
                .andExpect(jsonPath("$.name").value("New Product"))
                .andExpect(jsonPath("$.price").value(50.0));
    }

    @Test
    public void testGetAllProducts() throws Exception {
        // Arrange: Create a list of products
        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setName("Test Product");

        // Mock the service to return the list of products
        when(productService.getAllProducts()).thenReturn(Collections.singletonList(product));

        // Act and Assert: Perform the GET request and check the response
        mockMvc.perform(get("/api/v1/products")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)) // Add JWT token here
                .andExpect(status().isOk())  // Status should be 200
                .andExpect(jsonPath("$[0].name").value("Test Product"));
    }

    @Test
    public void testGetProductById() throws Exception {
        // Arrange: Create a DTO for the product
        UUID productId = UUID.randomUUID();
        ProductDto productDto = new ProductDto();
        productDto.setId(productId);
        productDto.setName("Test Product");

        // Mock the service to return the product
        when(productService.getProductById(productId)).thenReturn(productDto);

        // Act and Assert: Perform the GET request and check the response
        mockMvc.perform(get("/api/v1/products/{id}", productId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)) // Add JWT token here
                .andExpect(status().isOk())  // Status should be 200
                .andExpect(jsonPath("$.name").value("Test Product"));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        // Arrange: Create a DTO for the updated product
        UUID productId = UUID.randomUUID();
        ProductDto updatedProductDto = new ProductDto();
        updatedProductDto.setId(productId);
        updatedProductDto.setName("Updated Product");

        // Mock the service to return the updated product
        when(productService.updateProduct(productId, updatedProductDto)).thenReturn(updatedProductDto);

        // Act and Assert: Perform the PATCH request and check the response
        mockMvc.perform(patch("/api/v1/products/{id}", productId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken) // Add JWT token here
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Updated Product\", \"price\": 75.0 }"))
                .andExpect(status().isOk())  // Status should be 200
                .andExpect(jsonPath("$.name").value("Updated Product"))
                .andExpect(jsonPath("$.price").value(75.0));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        // Arrange: Create a UUID for the product to be deleted
        UUID productId = UUID.randomUUID();

        // Act and Assert: Perform the DELETE request and check the response
        mockMvc.perform(delete("/api/v1/products/{id}", productId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)) // Add JWT token here
                .andExpect(status().isNoContent());  // Status should be 204
    }
}
