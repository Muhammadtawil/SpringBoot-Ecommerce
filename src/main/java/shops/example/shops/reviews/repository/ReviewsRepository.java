package shops.example.shops.reviews.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import shops.example.shops.reviews.entity.Review;

import java.util.List;
import java.util.UUID;
public interface ReviewsRepository extends JpaRepository<Review, UUID> {
    List<Review> findByUser(UUID userId);

}
