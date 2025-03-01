package shops.example.shops.reviews.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import shops.example.shops.reviews.dto.ReviewDto;
import shops.example.shops.reviews.entity.Review;
import shops.example.shops.reviews.service.ReviewService;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewsController {
    private final ReviewService reviewService;

    public  ReviewsController(ReviewService reviewService ){
        this.reviewService=reviewService;
    }

    @GetMapping
        public ResponseEntity<List<ReviewDto>> getAllReviews() {
        List<Review> review = reviewService.getAllReviews();
        List<ReviewDto> reviewDtos = review.stream()
                .map(ReviewDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reviewDtos);
    }

    @GetMapping("/me")
    public ResponseEntity<List<ReviewDto>> getMyReviews(){
        List<Review> review = reviewService.getMyReviews();
        List<ReviewDto> reviewDtos = review.stream()
        .map(ReviewDto::fromEntity)
        .collect(Collectors.toList());
return ResponseEntity.ok(reviewDtos);
    }

@PostMapping("/add")
public ResponseEntity<ReviewDto> addProductReview(@RequestBody ReviewDto reviewDto){
    ReviewDto review = reviewService.addProductReview(reviewDto);
    return ResponseEntity.ok(review);
}

@PatchMapping("/update/{id}")  // Ensure the correct URL path variable is defined here
public ResponseEntity<ReviewDto> updateProductReview(@PathVariable UUID id, @RequestBody ReviewDto reviewDto) {
    ReviewDto review = reviewService.updateProductReview(id, reviewDto); // Use the id passed from the path
    return ResponseEntity.ok(review);
}

@DeleteMapping("remove/{id}")
public ResponseEntity<Void> deleteReview(@PathVariable UUID id){
    reviewService.deleteReview(id);
    return ResponseEntity.noContent().build();
}



}
