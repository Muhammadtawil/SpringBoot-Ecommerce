    package shops.example.shops.categories.dto;

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

        public CategoryDTO() {
        }

        public CategoryDTO(UUID id, String name, String description, UUID parentCategory, List<UUID> subCategories) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.parentCategory = parentCategory;
            this.subCategories = subCategories;
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
        
        public static CategoryDTO fromEntity(Category category) {
            return new CategoryDTO(
                    category.getId(),
                    category.getName(),
                    category.getDescription(),
                    category.getParentCategory() != null ? category.getParentCategory().getId() : null,
                    category.getSubCategories() != null ? category.getSubCategories().stream()
                            .map(Category::getId)
                            .collect(Collectors.toList()) : null
            );
        }
        public static Category toEntity(CategoryDTO categoryDTO) {
            Category category = new Category();
            category.setId(categoryDTO.getId());
            category.setName(categoryDTO.getName());
            category.setDescription(categoryDTO.getDescription());
            // Parent and subcategories should be set in the service layer
            return category;
        }
    }