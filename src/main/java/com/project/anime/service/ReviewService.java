package com.project.anime.service;

import com.project.anime.aop.annotation.Logging;
import com.project.anime.cache.CacheEntity;
import com.project.anime.dto.review.CreateReview;
import com.project.anime.entity.Anime;
import com.project.anime.entity.Review;
import com.project.anime.repository.AnimeRepository;
import com.project.anime.repository.ReviewRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Logging
@Service
public class ReviewService {
  private final ReviewRepository reviewRepository;
  private final AnimeRepository animeRepository;
  private final CacheEntity<Integer, Review> cache;

  public ReviewService(ReviewRepository reviewRepository, AnimeRepository animeRepository,
                       CacheEntity<Integer, Review> cache) {
    this.reviewRepository = reviewRepository;
    this.animeRepository = animeRepository;
    this.cache = cache;
  }

  public void createReview(CreateReview review) {
    Review newReview = new Review(review.getTitle(), review.getText(), review.getGrade());
    Anime anime = animeRepository.findById(review.getAnimeId())
        .orElseThrow(() -> new RuntimeException("Anime not found"));
    newReview.setAnime(anime);
    anime.addReview(newReview);
    anime.setReviewCount(anime.getReviewCount() + 1);
    anime.setTotalRating(anime.getTotalRating() + newReview.getGrade());
    reviewRepository.save(newReview);
  }

  public List<Review> getAllReviews() {
    return reviewRepository.findAll();
  }

  public Review getReviewById(Integer id) {
    Review review = cache.get(id);
    if (review == null) {
      review = reviewRepository.findById(id).orElseThrow(() -> new RuntimeException("q"));
    }

    return review;
  }

  public void updateReview(Integer id, Review newReview) {
    newReview.setId(id);
    cache.remove(id);
    reviewRepository.save(newReview);
  }

  public void partialUpdateReview(Integer id, Review updates) {
    Review review =
        reviewRepository.findById(id).orElseThrow(() -> new RuntimeException("Review not found"));

    if (review.getTitle() != null) {
      review.setTitle(updates.getTitle());
    }
    if (review.getText() != null) {
      review.setText(updates.getText());
    }
    if (review.getGrade() != null) {
      review.setGrade(updates.getGrade());
    }
    cache.remove(id);
    reviewRepository.save(review);
  }

  public void deleteReview(Integer id) {
    cache.remove(id);
    reviewRepository.deleteById(id);
  }

}
