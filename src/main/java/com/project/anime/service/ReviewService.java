package com.project.anime.service;

import com.project.anime.dto.review.CreateReview;
import com.project.anime.entity.Anime;
import com.project.anime.entity.Review;
import com.project.anime.repository.AnimeRepository;
import com.project.anime.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final AnimeRepository animeRepository;

    public ReviewService(ReviewRepository reviewRepository, AnimeRepository animeRepository){
        this.reviewRepository = reviewRepository;
        this.animeRepository = animeRepository;
    }

    public void createReview(CreateReview review){
        Review newReview = new Review(review.getTitle(), review.getText(), review.getGrade());
        Anime anime = animeRepository.findById(review.getAnimeId()).orElseThrow(() -> new RuntimeException("Anime not found"));
        newReview.setAnime(anime);
        anime.getReviews().add(newReview);
        anime.setReviewCount(anime.getReviewCount() + 1);
        anime.setTotalRating(anime.getTotalRating() + newReview.getGrade());
        reviewRepository.save(newReview);
    }

    public List<Review> getAllReviews(){
        return reviewRepository.findAll();
    }

    public Optional<Review> getReviewById(Integer id){
        return reviewRepository.findById(id);
    }

    public void updateReview(Integer id, Review newReview){
        newReview.setId(id);
        reviewRepository.save(newReview);
    }

    public void partialUpdateReview(Integer id, Review updates){
        Review review = reviewRepository.findById(id).orElseThrow(() -> new RuntimeException("Review not found"));

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

    public void deleteReview(Integer id){
        reviewRepository.deleteById(id);
    }

}
