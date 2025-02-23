package shops.example.shops.products.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import shops.example.shops.products.entity.Product;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}