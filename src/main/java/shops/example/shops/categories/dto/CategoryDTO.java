package shops.example.shops.categories.dto;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import shops.example.shops.categories.entity.Category;

@AllArgsConstructor
@Data
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