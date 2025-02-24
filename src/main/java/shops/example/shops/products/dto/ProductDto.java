package shops.example.shops.products.dto;

import shops.example.shops.products.entity.Product;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ProductDto {
    private UUID id;
    private String name;
    private String description;
    private String sku;
    private String barcode;
    private double weight;
    private String dimensions;
    private BigDecimal originalPrice;
    private BigDecimal discount;
    private BigDecimal quantity;
    private BigDecimal discountedPrice;
    private boolean isInStock;
    private boolean isDraft;
    private List<String> images;
    private UUID categoryId;
    private UUID brand;  // This is a UUID, which is correct
    private Date createdAt;
    private Date updatedAt;
    private UUID createdBy;
    private UUID updatedBy;



    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(BigDecimal discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public boolean isInStock() {
        return isInStock;
    }

    public void setInStock(boolean inStock) {
        isInStock = inStock;
    }

    public boolean isDraft() {
        return isDraft;
    }

    public void setDraft(boolean draft) {
        isDraft = draft;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public UUID getBrand() {
        return brand;
    }

    public void setBrand(UUID brandId) {
        this.brand = brandId;
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
    public UUID getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UUID createdBy) {
        this.createdBy = createdBy;
    }

    public UUID getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(UUID updatedBy) {
        this.updatedBy = updatedBy;
    }

    public static ProductDto fromEntity(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setSku(product.getSku());
        productDto.setBarcode(product.getBarcode());
        productDto.setWeight(product.getWeight());
        productDto.setDimensions(product.getDimensions());
        productDto.setOriginalPrice(product.getOriginalPrice());
        productDto.setDiscount(product.getDiscount());
        productDto.setQuantity(product.getQuantity());
        productDto.setDiscountedPrice(product.getDiscountedPrice());
        productDto.setInStock(product.isInStock());
        productDto.setDraft(product.isDraft());
        productDto.setImages(product.getImages());
        productDto.setCategoryId(product.getCategory().getId());
        productDto.setBrand(product.getBrand().getId());  // Here, we store the brand's UUID
        productDto.setCreatedAt(product.getCreatedAt());
        productDto.setUpdatedAt(product.getUpdatedAt());
        productDto.setCreatedBy(product.getCreatedBy());
        productDto.setUpdatedBy(product.getUpdatedBy());
        return productDto;
    }

    
    public static Product toEntity(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setSku(productDto.getSku());
        product.setBarcode(productDto.getBarcode());
        product.setWeight(productDto.getWeight());
        product.setDimensions(productDto.getDimensions());
        product.setOriginalPrice(productDto.getOriginalPrice());
        product.setDiscount(productDto.getDiscount());
        product.setQuantity(productDto.getQuantity());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setInStock(productDto.isInStock());
        product.setDraft(productDto.isDraft());
        product.setImages(productDto.getImages());
        // Category and Brand should be set in the service layer
        return product;
    }
}
