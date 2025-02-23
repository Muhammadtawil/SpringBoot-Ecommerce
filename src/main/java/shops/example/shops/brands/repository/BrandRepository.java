package shops.example.shops.brands.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shops.example.shops.brands.entity.Brands;

import java.util.UUID;

public interface BrandRepository extends JpaRepository<Brands, UUID> {
}
