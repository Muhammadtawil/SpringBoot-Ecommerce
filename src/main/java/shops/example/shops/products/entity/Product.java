package shops.example.shops.products.entity;

import jakarta.persistence.*;
import lombok.Data;
import shops.example.shops.categories.entity.Category;
import shops.example.shops.brands.entity.Brands;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "products", indexes = {
    @Index(name = "idx_product_name", columnList = "name"),
    @Index(name = "idx_product_sku", columnList = "sku"),
    @Index(name = "idx_product_barcode", columnList = "barcode")
})
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "sku", nullable = false, unique = true)
    private String sku;

    @Column(name = "barcode", nullable = false, unique = true)
    private String barcode;

    @Column(name = "weight", nullable = false)
    private double weight;

    @Column(name = "dimensions", nullable = false)
    private String dimensions;

    @Column(name = "original_price", nullable = false)
    private BigDecimal originalPrice;

    @Column(name = "discount", nullable = false)
    private BigDecimal discount;

    @Column(name = "quantity", nullable = false)
    private BigDecimal quantity;

    @Column(name = "discounted_price", nullable = false)
    private BigDecimal discountedPrice;

    @Column(name = "is_in_stock", nullable = true)
    private boolean isInStock;

    @Column(name = "is_draft", nullable = true)
    private boolean isDraft = true;

    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url", nullable = true)
    private List<String> images;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brands brand;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "created_by", nullable = false)
    private UUID createdBy;

    @Column(name = "updated_by", nullable = true)
    private UUID updatedBy;

    // Method to update the discounted price
    private void updateDiscountedPrice() {
        if (originalPrice != null && discount != null && discount.compareTo(BigDecimal.ZERO) > 0) {
            this.discountedPrice = originalPrice.subtract(originalPrice.multiply(discount));
        } else {
            this.discountedPrice = originalPrice;
        }
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
        updateDiscountedPrice();
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
        updateDiscountedPrice();
    }

     
	}



