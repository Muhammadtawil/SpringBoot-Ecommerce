package shops.example.shops.categories.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import shops.example.shops.auth.entity.User;
import shops.example.shops.auth.service.CustomUserDetails;
import shops.example.shops.categories.dto.CategoryDTO;
import shops.example.shops.categories.entity.Category;
import shops.example.shops.categories.repository.CategoryRepository;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public CategoryDTO getCategoryById(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return CategoryDTO.fromEntity(category);
    }

    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User currentUser = customUserDetails.getUser();

        Category category = CategoryDTO.toEntity(categoryDTO);

        if (categoryDTO.getParentCategory() != null) {
            Category parentCategory = categoryRepository.findById(categoryDTO.getParentCategory())
                    .orElseThrow(() -> new RuntimeException("Parent category not found"));

            category.setParentCategory(parentCategory);
            parentCategory.getSubCategories().add(category);
        
            // categoryRepository.save(parentCategory);
        }

    
        category.setCreatedBy(currentUser.getId());
        category.setUpdatedBy(currentUser.getId());
        return CategoryDTO.fromEntity(categoryRepository.save(category));
    }

    public void updateCategoryFields(Category category, CategoryDTO categoryDTO) {
        // Loop through the fields of the CategoryDTO
        for (Field field : CategoryDTO.class.getDeclaredFields()) {
            field.setAccessible(true); // Make private fields accessible
    
            try {
                // Check if the field value in DTO is not null
                Object value = field.get(categoryDTO);
                if (value != null && !field.getName().equals("parentCategory")) { // Skip parentCategory
                    // Use reflection to set the field in the Category entity
                    Field entityField = Category.class.getDeclaredField(field.getName());
                    entityField.setAccessible(true);
                    entityField.set(category, value);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                // Handle any reflection errors
                e.printStackTrace();
            }
        }
    }

    public CategoryDTO updateCategory(UUID id, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    
        // Perform dynamic update using reflection
        updateCategoryFields(category, categoryDTO);
    
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User currentUser = customUserDetails.getUser();
    
        // Ensure the updated user is set for the entity
        category.setUpdatedBy(currentUser.getId());
    
        // Handle the parent category update separately
        if (categoryDTO.getParentCategory() == null || categoryDTO.getParentCategory().toString().isEmpty()) {
            category.setParentCategory(null);
        } else {
            Category parentCategory = categoryRepository.findById(categoryDTO.getParentCategory())
                    .orElseThrow(() -> new RuntimeException("Parent category not found"));
            category.setParentCategory(parentCategory);
        }
    
        return CategoryDTO.fromEntity(categoryRepository.save(category));
    }
    
    public void deleteCategory(UUID id) {
        categoryRepository.deleteById(id);
    }
}