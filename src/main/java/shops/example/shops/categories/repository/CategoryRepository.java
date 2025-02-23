

package shops.example.shops.categories.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import shops.example.shops.categories.entity.Category;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
