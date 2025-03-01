package shops.example.shops.reviews.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import shops.example.shops.auth.entity.User;
import shops.example.shops.auth.entity.enums.UserRole;
import shops.example.shops.auth.service.AuthenticationService;
import shops.example.shops.products.entity.Product;
import shops.example.shops.products.repository.ProductRepository;
import shops.example.shops.reviews.dto.ReviewDto;
import shops.example.shops.reviews.entity.Review;
import shops.example.shops.reviews.repository.ReviewsRepository;

@Service
public class ReviewService {
private final ReviewsRepository reviewsRepository;
private final AuthenticationService authenticationService;
private final ProductRepository productRepository;

public ReviewService(ReviewsRepository reviewsRepository, AuthenticationService authenticationService,ProductRepository productRepository) {
this.reviewsRepository = reviewsRepository;
this.authenticationService = authenticationService;
this.productRepository = productRepository;
}


public ReviewDto addProductReview(ReviewDto reviewDto) {
    User currentUser = authenticationService.getCurrentUser();
    UUID userId = currentUser.getId(); 

    // Retrieve the Product using the UUID from the request
    Product product = productRepository.findById(reviewDto.getProduct())
                                       .orElseThrow(() -> new RuntimeException("Product not found"));

    // Create Review entity and set values
    Review review = new Review();
    review.setUser(userId);
    review.setContent(reviewDto.getContent());
    review.setRating(reviewDto.getRating());
    review.setProduct(product);  // Set the actual Product object
    review.setPublished(reviewDto.isPublished());

    // Save the Review to the repository
    review = reviewsRepository.save(review);

    return ReviewDto.fromEntity(review);
}


public List<Review> getAllReviews(){
    User currentUser = authenticationService.getCurrentUser();

if(currentUser.getUserRole() !=UserRole.ADMIN && currentUser.getUserRole() !=UserRole.SUPER_ADMIN){
    throw new RuntimeException("You are not authorized to view this page");
}
    return reviewsRepository.findAll();
}

public List<Review> getMyReviews() {
    User currentUser = authenticationService.getCurrentUser();
    UUID userId = currentUser.getId();

    return reviewsRepository.findByUser(userId);

}


public ReviewDto updateProductReview(UUID reviewId, ReviewDto reviewDto) {
    User currentUser = authenticationService.getCurrentUser();
    UUID userId = currentUser.getId();

    // Retrieve the Review using the UUID from the URL
    Review review = reviewsRepository.findById(reviewId)
                                     .orElseThrow(() -> new RuntimeException("Review not found"));

    // Check if the current user is the owner of the review or has admin privileges
    if (!review.getUser().equals(userId) && currentUser.getUserRole() != UserRole.ADMIN && currentUser.getUserRole() != UserRole.SUPER_ADMIN) {
        throw new RuntimeException("You are not authorized to update this review");
    }

    // Update the Review entity with new values from the DTO (only those provided)
    if (reviewDto.getContent() != null) {
        review.setContent(reviewDto.getContent());
    }
    if (reviewDto.getRating() != 0) {  // Assuming rating is an int and 0 is an invalid value
        review.setRating(reviewDto.getRating());
    }
    if (reviewDto.getProduct() != null) {
        Product product = productRepository.findById(reviewDto.getProduct())
                                           .orElseThrow(() -> new RuntimeException("Product not found"));
        review.setProduct(product);
    }
    if (reviewDto.isPublished() != review.isPublished()) {
        review.setPublished(reviewDto.isPublished());
    }

    // Save the updated Review to the repository
    review = reviewsRepository.save(review);

    return ReviewDto.fromEntity(review);
}


public void deleteReview(UUID id) {
    User currentUser = authenticationService.getCurrentUser();

    if(currentUser.getUserRole() !=UserRole.ADMIN && currentUser.getUserRole() !=UserRole.SUPER_ADMIN){
        throw new RuntimeException("You are not authorized to delete this page");
    }
    reviewsRepository.deleteById(id);
}



}