package com.project.anime.controller;

import com.project.anime.dto.review.CreateReview;
import com.project.anime.entity.Review;
import com.project.anime.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
    @PostMapping
    public ResponseEntity<Void> createReview(@RequestBody CreateReview review){
        reviewService.createReview(review);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(){
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Integer id){
        return ResponseEntity.of(reviewService.getReviewById(id)); //404 if not present()
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateReview(@PathVariable Integer id, Review newReview){
        reviewService.updateReview(id, newReview);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> partialUpdateReview(@PathVariable Integer id, @RequestBody Review updates){
        reviewService.partialUpdateReview(id, updates);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Integer id){
        reviewService.deleteReview(id);
        return ResponseEntity.ok().build();
    }
}
