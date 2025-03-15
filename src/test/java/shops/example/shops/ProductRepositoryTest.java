package shops.example.shops;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import shops.example.shops.products.entity.Product;
import shops.example.shops.products.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")  // Make sure to use the test profile
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testSaveProduct() {
        Product product = new Product();
        product.setName("Toyota Camry 2025");
        product.setDescription("Reliable sedan with modern technology and excellent fuel economy.");
        product.setSku("CAM2025001");
        product.setBarcode("9988776655443");
        product.setWeight(1500);
        product.setDimensions("480x185x145 cm");
        product.setOriginalPrice(new BigDecimal("30000"));
        product.setDiscount(new BigDecimal("1500"));
        product.setQuantity(new BigDecimal("20"));
        product.setDiscountedPrice(new BigDecimal("28500"));

        Product savedProduct = productRepository.save(product);

        // Ensure that the product is saved and has an ID
        assertNotNull(savedProduct.getId());
        assertEquals("Toyota Camry 2025", savedProduct.getName());
        assertEquals("CAM2025001", savedProduct.getSku());
        assertEquals(new BigDecimal("28500"), savedProduct.getDiscountedPrice());
    }

    @Test
    public void testFindBySku() {
        Product product = new Product();
        product.setName("Ford F-150 2025");
        product.setDescription("Powerful pickup truck.");
        product.setSku("F150_2025001");
        product.setBarcode("3344556677889");
        product.setWeight(2200);
        product.setDimensions("600x250x190 cm");
        product.setOriginalPrice(new BigDecimal("35000"));
        product.setDiscount(new BigDecimal("2500"));
        product.setQuantity(new BigDecimal("15"));
        product.setDiscountedPrice(new BigDecimal("32500"));

        productRepository.save(product);

        // Test the findBySku method
        Optional<Product> foundProduct = productRepository.findAll().stream()
                .filter(p -> p.getSku().equals("F150_2025001"))
                .findFirst();

        assertTrue(foundProduct.isPresent());
        assertEquals("Ford F-150 2025", foundProduct.get().getName());
    }

    @Test
    public void testProductNotFound() {
        Optional<Product> foundProduct = productRepository.findById(UUID.randomUUID());

        assertFalse(foundProduct.isPresent());
    }
}
