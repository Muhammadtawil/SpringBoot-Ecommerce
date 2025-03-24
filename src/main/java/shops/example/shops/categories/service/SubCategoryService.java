// package shops.example.shops.categories.service;   
// import org.springframework.stereotype.Service;

// import shops.example.shops.categories.entity.SubCategory;
// import shops.example.shops.categories.repository.SubCategoryRepository;

// import java.util.List;
// import java.util.UUID;

// @Service
// public class SubCategoryService {
//     private final SubCategoryRepository subCategoryRepository;

//     public SubCategoryService(SubCategoryRepository subCategoryRepository) {
//         this.subCategoryRepository = subCategoryRepository;
//     }

//     public List<SubCategory> getAllSubCategories() {
//         return subCategoryRepository.findAll();
//     }

//     public SubCategory getSubCategoryById(UUID id) {
//         return subCategoryRepository.findById(id).orElseThrow(() -> new RuntimeException("SubCategory not found"));
//     }

//     public SubCategory createSubCategory(SubCategory subCategory) {
//         return subCategoryRepository.save(subCategory);
//     }

//     public SubCategory updateSubCategory(UUID id, SubCategory subCategoryDetails) {
//         SubCategory subCategory = getSubCategoryById(id);
//         subCategory.setName(subCategoryDetails.getName());
//         subCategory.setParentCategory(subCategoryDetails.getParentCategory());
//         return subCategoryRepository.save(subCategory);
//     }

//     public void deleteSubCategory(UUID id) {
//         subCategoryRepository.deleteById(id);
//     }
// }
