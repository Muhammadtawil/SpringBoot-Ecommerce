package shops.example.shops.products.service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import shops.example.shops.auth.entity.User;
import shops.example.shops.auth.service.CustomUserDetails;
import shops.example.shops.brands.entity.Brands;
import shops.example.shops.brands.repository.BrandRepository;
import shops.example.shops.categories.entity.Category;
import shops.example.shops.categories.repository.CategoryRepository;
import shops.example.shops.products.dto.ProductDto;
import shops.example.shops.products.entity.Product;
import shops.example.shops.products.repository.ProductRepository;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
     private final BrandRepository brandsRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, BrandRepository brandsRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.brandsRepository = brandsRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public ProductDto getProductById(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return ProductDto.fromEntity(product);
    }

    public ProductDto createProduct(ProductDto productDto) {
          Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User currentUser = customUserDetails.getUser();

        Product product= ProductDto.toEntity(productDto);

        product.setCreatedBy(currentUser.getId());
        product.setUpdatedBy(currentUser.getId());

             // Convert UUID to Brand entity
        Brands brand = brandsRepository.findById(productDto.getBrand())
                .orElseThrow(() -> new RuntimeException("Brand not found"));
        product.setBrand(brand);

        // Convert UUID to Category entity
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        product.setCategory(category);

        return ProductDto.fromEntity(productRepository.save(product));
    }

      public List<ProductDto> createProducts(List<ProductDto> productDtos) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User currentUser = customUserDetails.getUser();

        List<Product> products = productDtos.stream().map(productDto -> {
            Product product = ProductDto.toEntity(productDto);

            product.setCreatedBy(currentUser.getId());
            product.setUpdatedBy(currentUser.getId());

            // Convert UUID to Brand entity
            Brands brand = brandsRepository.findById(productDto.getBrand())
                    .orElseThrow(() -> new RuntimeException("Brand not found"));
            product.setBrand(brand);

            // Convert UUID to Category entity
            Category category = categoryRepository.findById(productDto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            product.setCategory(category);

            return product;
        }).collect(Collectors.toList());

        return productRepository.saveAll(products).stream()
                .map(ProductDto::fromEntity)
                .collect(Collectors.toList());
    }

public ProductDto updateProduct(UUID id, ProductDto productDetails) {
    Product product = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found"));
    
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
    User currentUser = customUserDetails.getUser();

    product.setUpdatedBy(currentUser.getId());

    // Iterate through all fields in ProductDto and update the product entity fields
    for (Field field : ProductDto.class.getDeclaredFields()) {
        try {
            field.setAccessible(true);
            Object value = field.get(productDetails);
            if (value != null) {
                Field productField = Product.class.getDeclaredField(field.getName());
                productField.setAccessible(true);
                productField.set(product, value);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // Handle any potential errors (e.g., if a field does not exist in the Product entity)
            e.printStackTrace();
        }
    }

    // Handle Category update (assuming Category is a foreign key relation)
    if (productDetails.getCategoryId() != null) {
        Category category = categoryRepository.findById(productDetails.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        product.setCategory(category);
    }



    return ProductDto.fromEntity(productRepository.save(product));
}

    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }
}
