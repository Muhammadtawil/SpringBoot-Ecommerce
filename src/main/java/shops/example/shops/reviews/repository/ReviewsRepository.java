package shops.example.shops.reviews.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import shops.example.shops.reviews.entity.Review;

import java.util.UUID;
public interface ReviewsRepository extends JpaRepository<Review, UUID> {
    Review findByUserId(UUID userId);
}
