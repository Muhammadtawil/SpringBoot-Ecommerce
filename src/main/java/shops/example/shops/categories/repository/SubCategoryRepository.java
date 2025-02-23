package shops.example.shops.categories.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import shops.example.shops.categories.entity.SubCategory;

import java.util.UUID;

public interface SubCategoryRepository extends JpaRepository<SubCategory, UUID> {
}
