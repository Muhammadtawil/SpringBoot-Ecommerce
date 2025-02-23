    package shops.example.shops.categories.entity;

    import jakarta.persistence.*;
    import lombok.Data;
    import lombok.NoArgsConstructor;
    import org.hibernate.annotations.CreationTimestamp;
    import org.hibernate.annotations.UpdateTimestamp;

    import java.util.Date;
    import java.util.List;
    import java.util.UUID;

    @Entity
    @Table(name = "categories", indexes = {
        @Index(name = "idx_category_name", columnList = "name")
    })
    @Data
    @NoArgsConstructor
    public class Category {
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private UUID id;

        @Column(name = "name", nullable = false)
        private String name;

        @Column(name = "description", nullable = false)
        private String description;

        @CreationTimestamp
        @Column(updatable = false, name = "created_at")
        private Date createdAt;

        @UpdateTimestamp
        @Column(name = "updated_at")
        private Date updatedAt;

        @Column(name = "created_by", nullable = false)
        private UUID createdBy;

        @Column(name = "updated_by", nullable = true)
        private UUID updatedBy;

        @Column(name = "category_image", nullable = true)
        private String categoryImage;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "parent_category_id")
        private Category parentCategory;

        @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
        private List<Category> subCategories;
    }
