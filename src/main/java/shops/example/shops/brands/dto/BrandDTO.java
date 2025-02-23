package shops.example.shops.brands.dto;

import java.util.UUID;

import shops.example.shops.brands.entity.Brands;

public class BrandDTO {
    private UUID id;
    private String name;
    private String description;

    public BrandDTO() {
    }

    public BrandDTO(UUID id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public static BrandDTO fromEntity(Brands brand) {
        return new BrandDTO(
                brand.getId(),
                brand.getName(),
                brand.getDescription()
        );
    }

    public static Brands toEntity(BrandDTO brandDTO) {
        Brands brand = new Brands();
        brand.setId(brandDTO.getId());
        brand.setName(brandDTO.getName());
        brand.setDescription(brandDTO.getDescription());
        return brand;
    }
}
