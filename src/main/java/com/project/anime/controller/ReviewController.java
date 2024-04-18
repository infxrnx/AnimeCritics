package com.project.anime.controller;

import com.project.anime.dto.review.CreateReview;
import com.project.anime.entity.Review;
import com.project.anime.service.ReviewService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
  private static final String DELETE_SUCCESS_MESSAGE = "Deleted successfully!";
  private static final String UPDATE_SUCCESS_MESSAGE = "Updated successfully!";
  private static final String CREATE_SUCCESS_MESSAGE = "Created successfully!";
  private final ReviewService reviewService;

  public ReviewController(ReviewService reviewService) {
    this.reviewService = reviewService;
  }

  @PostMapping
  public ResponseEntity<String> createReview(@RequestBody CreateReview review) {
    reviewService.createReview(review);
    return new ResponseEntity<>(CREATE_SUCCESS_MESSAGE, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<Review>> getAllReviews() {
    return new ResponseEntity<>(reviewService.getAllReviews(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Review> getReviewById(@PathVariable Integer id) {
    return new ResponseEntity<>(reviewService.getReviewById(id),
        HttpStatus.OK); //404 if not present()
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> updateReview(@PathVariable Integer id, Review newReview) {
    reviewService.updateReview(id, newReview);
    return new ResponseEntity<>(UPDATE_SUCCESS_MESSAGE, HttpStatus.OK);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<String> partialUpdateReview(@PathVariable Integer id,
                                                    @RequestBody Review updates) {
    reviewService.partialUpdateReview(id, updates);
    return new ResponseEntity<>(UPDATE_SUCCESS_MESSAGE, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteReview(@PathVariable Integer id) {
    reviewService.deleteReview(id);
    return new ResponseEntity<>(DELETE_SUCCESS_MESSAGE, HttpStatus.OK);
  }
}
