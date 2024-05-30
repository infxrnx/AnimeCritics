package com.project.anime.service;

import com.project.anime.aop.annotation.Logging;
import com.project.anime.aop.exception.ResourceNotFoundException;
import com.project.anime.dto.review.CreateReview;
import com.project.anime.entity.Anime;
import com.project.anime.entity.Review;
import com.project.anime.repository.AnimeRepository;
import com.project.anime.repository.ReviewRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Logging
@Service
public class ReviewService {
  private final ReviewRepository reviewRepository;
  private final AnimeRepository animeRepository;

  public ReviewService(ReviewRepository reviewRepository, AnimeRepository animeRepository) {
    this.reviewRepository = reviewRepository;
    this.animeRepository = animeRepository;
  }

  public List<Review> getReviewsByAnimeId(Integer animeId) {
    return reviewRepository.findAllByAnimeId(animeId);
  }

  public void createReview(CreateReview review) {
    Review newReview = new Review(review.getTitle(), review.getText(), review.getGrade());
    Anime anime = animeRepository.findById(review.getAnimeId())
        .orElseThrow(() -> new RuntimeException("Anime not found"));
    newReview.setAnime(anime);
    anime.setReviewsCount(anime.getReviewsCount() + 1);
    anime.addReview(newReview);
    anime.setTotalRating(anime.getTotalRating() + newReview.getGrade());
    reviewRepository.save(newReview);
  }

  public List<Review> getAllReviews() {
    return reviewRepository.findAll();
  }

  public Review getReviewById(Integer id) {
    return reviewRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Review was not found"));
  }

  public void updateReview(Integer id, Review newReview) {
    newReview.setId(id);
    reviewRepository.save(newReview);
  }

  public void partialUpdateReview(Integer id, Review updates) {
    Review review =
        reviewRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Review not found"));

    if (review.getTitle() != null) {
      review.setTitle(updates.getTitle());
    }
    if (review.getText() != null) {
      review.setText(updates.getText());
    }
    if (review.getGrade() != null) {
      review.setGrade(updates.getGrade());
    }
    reviewRepository.save(review);
  }

  public void deleteReview(Integer id) {
    reviewRepository.deleteById(id);
  }

}
