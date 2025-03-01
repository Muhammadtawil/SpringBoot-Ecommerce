package shops.example.shops.reviews.dto;

import java.sql.Date;
import java.util.UUID;

import lombok.Data;
import shops.example.shops.products.entity.Product;
import shops.example.shops.reviews.entity.Review;


@Data
public class ReviewDto {
    private UUID id;
    private UUID user;
    private String content;
    private UUID product;  // Change to UUID, not Product
    private boolean isPublished;
    private int rating;
    private Date createdAt;
    private Date updatedAt;

    public static ReviewDto fromEntity(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setUser(review.getUser());
        reviewDto.setContent(review.getContent());
        reviewDto.setProduct(review.getProduct().getId());  // Map product to its UUID
        reviewDto.setPublished(review.isPublished());
        reviewDto.setRating(review.getRating());
        reviewDto.setCreatedAt(review.getCreatedAt());
        reviewDto.setUpdatedAt(review.getUpdatedAt());
        return reviewDto;
    }

    public Review toEntity(Product product) {
        Review review = new Review();
        review.setId(this.getId());
        review.setUser(this.getUser());
        review.setContent(this.getContent());
        review.setProduct(product);  // Set the full Product object
        review.setPublished(this.isPublished());
        review.setRating(this.getRating());
        review.setCreatedAt(this.getCreatedAt());
        review.setUpdatedAt(this.getUpdatedAt());
        return review;
    }
}