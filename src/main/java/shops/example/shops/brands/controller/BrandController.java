package shops.example.shops.brands.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shops.example.shops.brands.dto.BrandDTO;
import shops.example.shops.brands.service.BrandService;
import shops.example.shops.products.dto.ProductDto;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/brands")
public class BrandController {
    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public ResponseEntity<List<BrandDTO>> getAllBrands() {
        return ResponseEntity.ok(brandService.getAllBrands());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrandDTO> getBrandById(@PathVariable UUID id) {
        return ResponseEntity.ok(brandService.getBrandById(id));
    }

    @PostMapping
    public ResponseEntity<BrandDTO> createBrand(@RequestBody BrandDTO brandDTO) {
        return ResponseEntity.ok(brandService.createBrand(brandDTO));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<BrandDTO>> createBrands(@RequestBody List<BrandDTO> brandDTOs) {
        return ResponseEntity.ok(brandService.createBrands(brandDTOs));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BrandDTO> updateBrand(@PathVariable UUID id, @RequestBody BrandDTO brandDTO) {
        return ResponseEntity.ok(brandService.updateBrand(id, brandDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable UUID id) {
        brandService.deleteBrand(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<List<ProductDto>> getProductsByBrandId(@PathVariable UUID id) {
        return ResponseEntity.ok(brandService.getProductsByBrandId(id));
    }
}
