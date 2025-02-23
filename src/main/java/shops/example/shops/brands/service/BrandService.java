package shops.example.shops.brands.service;

import org.springframework.stereotype.Service;
import shops.example.shops.brands.dto.BrandDTO;
import shops.example.shops.brands.entity.Brands;
import shops.example.shops.brands.repository.BrandRepository;
import shops.example.shops.products.dto.ProductDto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BrandService {
    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public List<BrandDTO> getAllBrands() {
        return brandRepository.findAll().stream()
                .map(BrandDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public BrandDTO getBrandById(UUID id) {
        Brands brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found"));
        return BrandDTO.fromEntity(brand);
    }

    public BrandDTO createBrand(BrandDTO brandDTO) {
        Brands brand = BrandDTO.toEntity(brandDTO);
        return BrandDTO.fromEntity(brandRepository.save(brand));
    }

    public List<BrandDTO> createBrands(List<BrandDTO> brandDTOs) {
        List<Brands> brands = brandDTOs.stream()
                .map(BrandDTO::toEntity)
                .collect(Collectors.toList());
        return brandRepository.saveAll(brands).stream()
                .map(BrandDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public BrandDTO updateBrand(UUID id, BrandDTO brandDTO) {
        Brands brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found"));
    
        // Update name only if the name is not null
        if (brandDTO.getName() != null) {
            brand.setName(brandDTO.getName());
        }
        
        // Retain the existing description if it is null in the update request
        if (brandDTO.getDescription() != null) {
            brand.setDescription(brandDTO.getDescription());
        }
    
        return BrandDTO.fromEntity(brandRepository.save(brand));
    }
    

    public List<ProductDto> getProductsByBrandId(UUID brandId) {
        Brands brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new RuntimeException("Brand not found"));
        return brand.getProducts().stream()
                .map(ProductDto::fromEntity)
                .collect(Collectors.toList());
    }

    public void deleteBrand(UUID id) {
        brandRepository.deleteById(id);
    }
}
