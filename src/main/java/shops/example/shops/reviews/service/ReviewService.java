package shops.example.shops.reviews.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import shops.example.shops.auth.entity.User;
import shops.example.shops.auth.service.AuthenticationService;
import shops.example.shops.reviews.dto.ReviewDto;
import shops.example.shops.reviews.entity.Review;
import shops.example.shops.reviews.repository.ReviewsRepository;

@Service
public class ReviewService {
private final ReviewsRepository reviewsRepository;
private final AuthenticationService authenticationService;

public ReviewService(ReviewsRepository reviewsRepository, AuthenticationService authenticationService) {
this.reviewsRepository = reviewsRepository;
this.authenticationService = authenticationService;
}

public ReviewDto addProductReview(ReviewDto reviewDto) {
    User currentUser = authenticationService.getCurrentUser();
    UUID userId = currentUser.getId(); 

    Review review =reviewsRepository.findByUserId(userId);
    // No Review
    if(review == null) {
        review = new Review();
        review.setUser(userId);
        review=reviewsRepository.save(review);
    }

    review.setContent(reviewDto.getContent());
    review.setRating(reviewDto.getRating());
    review.setProduct(reviewDto.getProduct());
    review.setPublished(reviewDto.isPublished());
    review=reviewsRepository.save(review);
    
    return ReviewDto.fromEntity(review);
}

public ReviewDto getReview(UUID userId) {
    Review review = reviewsRepository.findByUserId(userId);
    return ReviewDto.fromEntity(review);

}



}
