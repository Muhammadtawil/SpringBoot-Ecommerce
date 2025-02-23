package shops.example.shops.brands.entity;

import jakarta.persistence.*;
import lombok.Data;
import shops.example.shops.products.entity.Product;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "brands")
@Data
public class Brands {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description", nullable = true)
    private String description;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Product> products;
}
