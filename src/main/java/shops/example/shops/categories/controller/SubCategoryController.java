// package shops.example.shops.categories.controller;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import shops.example.shops.categories.entity.SubCategory;
// import shops.example.shops.categories.service.SubCategoryService;

// import java.util.List;
// import java.util.UUID;

// @RestController
// @RequestMapping("/subcategories")
// public class SubCategoryController {
//     private final SubCategoryService subCategoryService;

//     public SubCategoryController(SubCategoryService subCategoryService) {
//         this.subCategoryService = subCategoryService;
//     }

//     @GetMapping
//     public ResponseEntity<List<SubCategory>> getAllSubCategories() {
//         List<SubCategory> subCategories = subCategoryService.getAllSubCategories();
//         return ResponseEntity.ok(subCategories);
//     }

//     @GetMapping("/{id}")
//     public ResponseEntity<SubCategory> getSubCategoryById(@PathVariable UUID id) {
//         SubCategory subCategory = subCategoryService.getSubCategoryById(id);
//         return ResponseEntity.ok(subCategory);
//     }

//     @PostMapping
//     public ResponseEntity<SubCategory> createSubCategory(@RequestBody SubCategory subCategory) {
//         SubCategory createdSubCategory = subCategoryService.createSubCategory(subCategory);
//         return ResponseEntity.ok(createdSubCategory);
//     }

//     @PutMapping("/{id}")
//     public ResponseEntity<SubCategory> updateSubCategory(@PathVariable UUID id, @RequestBody SubCategory subCategoryDetails) {
//         SubCategory updatedSubCategory = subCategoryService.updateSubCategory(id, subCategoryDetails);
//         return ResponseEntity.ok(updatedSubCategory);
//     }

//     @DeleteMapping("/{id}")
//     public ResponseEntity<Void> deleteSubCategory(@PathVariable UUID id) {
//         subCategoryService.deleteSubCategory(id);
//         return ResponseEntity.noContent().build();
//     }
// }
