package shops.example.shops.products.service;
import org.springframework.stereotype.Service;

import shops.example.shops.products.entity.Product;
import shops.example.shops.products.repository.ProductRepository;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(UUID id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(UUID id, Product productDetails) {
        Product product = getProductById(id);
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setOriginalPrice(productDetails.getOriginalPrice());
        product.setDiscount(productDetails.getDiscount());
        product.setInStock(productDetails.isInStock());
        product.setCategory(productDetails.getCategory());
        
        return productRepository.save(product);
    }

    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }
}
