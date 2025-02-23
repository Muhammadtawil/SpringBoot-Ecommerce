package shops.example.shops.categories.dto;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import shops.example.shops.categories.entity.Category;

public class CategoryDTO {
    private UUID id;
    private String name;
    private String description;
    private UUID parentCategory;
    private List<UUID> subCategories;
    private Date createdAt;
    private Date updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
    private String categoryImage;

    public CategoryDTO() {
    }

    public CategoryDTO(UUID id, String name, String description, UUID parentCategory, List<UUID> subCategories, Date createdAt, Date updatedAt, UUID createdBy, UUID updatedBy, String categoryImage) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.parentCategory = parentCategory;
        this.subCategories = subCategories;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.categoryImage = categoryImage;
    }

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

    public UUID getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(UUID parentCategory) {
        this.parentCategory = parentCategory;
    }

    public List<UUID> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<UUID> subCategories) {
        this.subCategories = subCategories;
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

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public static CategoryDTO fromEntity(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getParentCategory() != null ? category.getParentCategory().getId() : null,
                category.getSubCategories() != null ? category.getSubCategories().stream()
                        .map(Category::getId)
                        .collect(Collectors.toList()) : null,
                category.getCreatedAt(),
                category.getUpdatedAt(),
                category.getCreatedBy(),
                category.getUpdatedBy(),
                category.getCategoryImage()
        );
    }

    public static Category toEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        category.setCreatedAt(categoryDTO.getCreatedAt());
        category.setUpdatedAt(categoryDTO.getUpdatedAt());
        category.setCreatedBy(categoryDTO.getCreatedBy());
        category.setUpdatedBy(categoryDTO.getUpdatedBy());
        category.setCategoryImage(categoryDTO.getCategoryImage());
        // Parent and subcategories should be set in the service layer
        return category;
    }
}